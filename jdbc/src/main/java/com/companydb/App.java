package com.companydb;

public class App {
    public static void main(String[] args) {
        String[] tables = {"employee", "branch", "client"};
        Object lock = new Object();

        for (String table : tables) {
            FetchDataThread thread = new FetchDataThread(table, lock);
            thread.start();
            try {
                thread.join(); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}