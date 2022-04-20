package com.kobra.money.controller;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.kobra.money.entity.Category;
import com.kobra.money.entity.Operation;
import com.kobra.money.include.UserException;
import com.kobra.money.model.CategoryModel;
import com.kobra.money.model.Model;
import com.kobra.money.model.OperationModel;
import com.kobra.money.view.OperationView;
import com.kobra.money.view.form.Form;
import com.kobra.money.view.form.add.AddOperationForm;

import java.util.HashMap;
import java.util.List;

public class OperationController extends Controller {
    private List<Operation> operations;
    private List<Category> categories;

    /* Model request queue */
    private ModelRequestQueue operationRequestQueue;
    private ModelRequestQueue categoryRequestQueue;

    /* Model */
    private OperationModel operationModel;
    private CategoryModel categoryModel;

    /* View для вывода операций */
    private OperationView operationView;

    /* View для добавления операции */
    private AddOperationForm addOperationView;

    private OperationController(Context context) {
        super(context);
    }

    public void setOperations(HashMap<String, String> args, Event event) {
        if(operationModel != null) {
            operationRequestQueue.add(new ModelRequest() {
                @Override
                public void request() {
                    operationModel.getItemsFromHTTP(args, new Model.GetEvent<Operation>() {
                        @Override
                        public void onSuccess(List<Operation> items) {
                            operations = items;
                            event.onSuccess();
                        }

                        @Override
                        public void onError(String error) {
                            event.onError(error);
                        }
                    });
                }
            });
        }
    }

    public void setCategories(HashMap<String, String> args, Event event) {
        if(categoryModel != null) {
            categoryRequestQueue.add(new ModelRequest() {
                @Override
                public void request() {
                    categoryModel.getItemsFromHTTP(args, new Model.GetEvent<Category>() {
                        @Override
                        public void onSuccess(List<Category> items) {
                            categories = items;
                            addOperationView.setCategories(categories);
                            if(event != null) event.onSuccess();
                        }

                        @Override
                        public void onError(String error) {
                            if(event != null) event.onError(error);
                        }
                    });
                }
            });
        }
    }

    public void update(Event event) {
        if(operationModel != null) {
            operationRequestQueue.add(new ModelRequest() {
                @Override
                public void request() {
                    operationModel.updateItemsFromHTTP(new Model.GetEvent<Operation>() {
                        @Override
                        public void onSuccess(List<Operation> items) {
                            operations = items;
                            print();
                            if (event != null) event.onSuccess();
                        }

                        @Override
                        public void onError(String error) {
                            if (event != null) event.onError(error);
                        }
                    });
                }
            });
        }
    }

    public void print() {
        if(operationView != null) {
            operationRequestQueue.add(new ModelRequest() {
                @Override
                public void request() {
                    operationView.setOperations(operations);
                    operationView.print();
                }
            });
        }
    }

    private void createOperationModel() {
        operationModel = new OperationModel(context);
        operationRequestQueue = new ModelRequestQueue(operationModel);
        operationModel.setReady(new Model.ReadyEvent() {
            @Override
            public void onReady() {
                while(!operationModel.isRun() && !operationRequestQueue.isEmpty()) {
                    ModelRequest modelRequest = operationRequestQueue.poll();
                    if(modelRequest != null) modelRequest.request();
                }
            }
        });
    }

    private void createCategoryModel() {
        categoryModel = new CategoryModel(context);
        categoryRequestQueue = new ModelRequestQueue(categoryModel);
        categoryModel.setReady(new Model.ReadyEvent() {
            @Override
            public void onReady() {
                while(!categoryModel.isRun() && !categoryRequestQueue.isEmpty()) {
                    ModelRequest modelRequest = categoryRequestQueue.poll();
                    if(modelRequest != null) modelRequest.request();
                }
            }
        });
    }

    private void setOperationView(OperationView operationView) {
        this.operationView = operationView;
    }

    private void setAddOperationView(View formView) {
        addOperationView = (AddOperationForm) new AddOperationForm.Builder(context)
                .setFormView(formView)
                .getForm();
        addOperationView.setSubmitEvent(new Form.Submit() {
            @Override
            public void onSuccess(HashMap<String, String> fields) {
                if(!fields.containsKey("user_id")) {
                    fields.put("user_id", Long.toString(AuthController.authUser.getId()));
                }
                operationRequestQueue.add(new ModelRequest() {
                    @Override
                    public void request() {
                        operationModel.addOperation(fields, new Model.Event() {
                            @Override
                            public void onSuccess() {
                                addOperationView.resetForm();
                                Toast.makeText(context, "Операция успешно добавлена", Toast.LENGTH_SHORT).show();
                                update(null);
                            }

                            @Override
                            public void onError(String error) {
                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void onError(UserException exception) {
                Toast.makeText(context, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class Builder {
        private OperationController controller;

        public Builder(Context context) {
            controller = new OperationController(context);
        }

        public Builder setViewOption(OperationView operationView) {
            controller.setOperationView(operationView);
            controller.createOperationModel();
            return this;
        }

        public Builder setAddOption(View formView) {
            controller.setAddOperationView(formView);
            controller.createCategoryModel();
            return this;
        }

        public OperationController getController() {
            return controller;
        }
    }
}
