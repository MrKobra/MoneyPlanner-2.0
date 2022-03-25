package com.kobra.money.include;

public class UserException {
    private int code;
    private String message;

    public UserException() {
        setCode(0);
    }

    public void setCode(int code) {
        this.code = code;
        this.message = getErrorMessageByTable(code);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static String getErrorMessageByTable(int code) {
        String message = new String();

        switch (code) {
            case 0:
                message = "Ошибок не найдено";
                break;
            case 1:
                message = "Имя пользователя должно быть больше " + Integer.toString(Validate.minUsernameLength) + " символов";
                break;
            case 2:
                message = "Пароль должен быть больше " + Integer.toString(Validate.minPasswordLength) + " символов";
                break;
            case 3:
                message = "Неверное имя пользователя или пароль";
                break;
            case 4:
                message = "Пользователя с таким именем не найдено";
                break;
            case 5:
                message = "Введите сумму";
                break;
            case 6:
                message = "Выберите категорию";
                break;
            case 7:
                message = "План не выбран";
                break;
            case 8:
                message = "Операция не выбрана";
                break;
            case 9:
                message = "Минимальная длина " + Validate.minNameLength + " символов";
                break;
            case 10:
                message = "Максимальная длина " + Validate.maxNameLength + " символов";
                break;
            case 11:
                message = "Счет не выбран";
                break;
            case 12:
                message = "Тип не выбран";
                break;
            case 13:
                message = "Иконка не выбрана";
                break;
            default:
                message = "Ошибка";
        }

        return message;
    }
}
