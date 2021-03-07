package com.company;public class User {

    /**
     * A class that models a User for Uno Online. The user has a name and a password.
     *
     * @author dancye, 2019
     * @author Paul Bonenfant, 2021
     */
    public class Main {

        private String name;
        private String password;

        /**
         * a constructor that takes in the user's name and password
         *
         * @param name
         * @param password
         */
        public Main(String name, String password) {
            this.name = name;
            this.password = password;
        }

        /**
         * The getter for the user name
         *
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * The setter for the user name
         *
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * The getter for the password
         *
         * @return the password
         */
        public String getPassword() {
            return password;
        }

        /**
         * A setter for the password
         *
         * @param password
         */
        public void setPassword(String password) {
            this.password = password;
        }

    }


}
