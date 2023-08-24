package ru.aston;

import ru.aston.util.DbInitializeUtil;

public class JavaApplication {

    public static void main(String[] args) {
        try {
            DbInitializeUtil.initDB();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
