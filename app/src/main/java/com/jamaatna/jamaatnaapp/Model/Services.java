package com.jamaatna.jamaatnaapp.Model;

import java.util.List;

public class Services {

    /**
     * message : تمت العملية بنجاح
     * status : 1
     * data : {"Services":[{"id":2,"name":"mahm","phone":"972595595238","tag":"مندي-ذبايح-بخاري-مضبي-فتة","logo":"http://mhmdstudio.com/azemty/public/admin/img/setting","status":true,"city":{"id":1,"name":"الرياض"},"user":{"id":16,"name":"وفاء حرب جامع","phone":"966789789789","photo":"http://mhmdstudio.com/azemty/public/img/user/Screenshot_2020-03-05-17-57-33-870_com.heartoftheworldapp.heartoftheworld.jpg","status":true}},{"id":4,"name":"mahm","phone":"972595595238","tag":"مندي-ذبايح-بخاري-مضبي-فتة","logo":"http://mhmdstudio.com/azemty/public/admin/img/setting","status":true,"city":{"id":1,"name":"الرياض"},"user":{"id":18,"name":"xcc","phone":"972595595238","photo":"http://mhmdstudio.com/azemty/public/admin/img/setting/35163665790_d182d84f5e_k.jpg","status":true}},{"id":5,"name":"mahm","phone":"972595595238","tag":"مندي-ذبايح-بخاري-مضبي-فتة","logo":"http://mhmdstudio.com/azemty/public/img/service/587.PNG","status":true,"city":{"id":1,"name":"الرياض"},"user":{"id":18,"name":"xcc","phone":"972595595238","photo":"http://mhmdstudio.com/azemty/public/admin/img/setting/35163665790_d182d84f5e_k.jpg","status":true}}]}
     */

    private String message;
    private int status;
    private DataBean data;

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
        private List<ServicesBean> Services;

        public List<ServicesBean> getServices() {
            return Services;
        }

        public void setServices(List<ServicesBean> Services) {
            this.Services = Services;
        }

        public static class ServicesBean {
            /**
             * id : 2
             * name : mahm
             * phone : 972595595238
             * tag : مندي-ذبايح-بخاري-مضبي-فتة
             * logo : http://mhmdstudio.com/azemty/public/admin/img/setting
             * status : true
             * city : {"id":1,"name":"الرياض"}
             * user : {"id":16,"name":"وفاء حرب جامع","phone":"966789789789","photo":"http://mhmdstudio.com/azemty/public/img/user/Screenshot_2020-03-05-17-57-33-870_com.heartoftheworldapp.heartoftheworld.jpg","status":true}
             */

            private int id;
            private String name;
            private String phone;
            private String tag;
            private String logo;
            private boolean status;
            private CityBean city;
            private UserBean user;

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

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public boolean isStatus() {
                return status;
            }

            public void setStatus(boolean status) {
                this.status = status;
            }

            public CityBean getCity() {
                return city;
            }

            public void setCity(CityBean city) {
                this.city = city;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public static class CityBean {
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

            public static class UserBean {
                /**
                 * id : 16
                 * name : وفاء حرب جامع
                 * phone : 966789789789
                 * photo : http://mhmdstudio.com/azemty/public/img/user/Screenshot_2020-03-05-17-57-33-870_com.heartoftheworldapp.heartoftheworld.jpg
                 * status : true
                 */

                private int id;
                private String name;
                private String phone;
                private String photo;
                private boolean status;

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

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public String getPhoto() {
                    return photo;
                }

                public void setPhoto(String photo) {
                    this.photo = photo;
                }

                public boolean isStatus() {
                    return status;
                }

                public void setStatus(boolean status) {
                    this.status = status;
                }
            }
        }
    }
}
