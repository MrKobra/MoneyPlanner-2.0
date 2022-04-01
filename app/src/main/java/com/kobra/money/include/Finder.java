package com.kobra.money.include;

import com.kobra.money.view.form.Form;

import java.util.List;

public class Finder {
    public static int searchByFieldName(String name, List<Form.FormField> fields) {
        int index = -1;

        if(fields != null && fields.size() > 0) {
            for (int i = 0; i < fields.size(); i++) {
                if (fields.get(i).getName().equals(name)) {
                    index = i;
                }
            }
        }

        return index;
    }
}
