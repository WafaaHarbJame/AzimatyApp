package com.jamaatna.jamaatnaapp.Model;

import java.util.List;

public class Cities {

    /**
     * message : تمت العملية بنجاح
     * status : 1
     * data : {"cities":[{"id":1,"name":"الرياض"},{"id":2,"name":"جدة"}]}
     */

    private String message;
    private int status;
    private DataBean data;

    public Cities(String message, int status, DataBean data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<CitiesBean> cities;

        public List<CitiesBean> getCities() {
            return cities;
        }

        public void setCities(List<CitiesBean> cities) {
            this.cities = cities;
        }

        public static class CitiesBean {
            /**
             * id : 1
             * name : الرياض
             */

            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
