package com.LianXiangKeJi.SupplyChain.regist.bean;

import java.util.List;

/**
 * @ClassName:RegistLogcationBean
 * @Author:hmy
 * @Description:java类作用描述
 */
public class RegistLogcationBean {

    /**
     * status : 1
     * info : OK
     * infocode : 10000
     * count : 1
     * geocodes : [{"formatted_address":"河南省洛阳市涧西区佰嘉宜超市","country":"中国","province":"河南省","citycode":"0379","city":"洛阳市","district":"涧西区","township":[],"neighborhood":{"name":[],"type":[]},"building":{"name":[],"type":[]},"adcode":"410305","street":[],"number":[],"location":"112.394552,34.644045","level":"兴趣点"}]
     */

    private String status;
    private String info;
    private String infocode;
    private String count;
    private List<GeocodesBean> geocodes;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<GeocodesBean> getGeocodes() {
        return geocodes;
    }

    public void setGeocodes(List<GeocodesBean> geocodes) {
        this.geocodes = geocodes;
    }

    public static class GeocodesBean {
        /**
         * formatted_address : 河南省洛阳市涧西区佰嘉宜超市
         * country : 中国
         * province : 河南省
         * citycode : 0379
         * city : 洛阳市
         * district : 涧西区
         * township : []
         * neighborhood : {"name":[],"type":[]}
         * building : {"name":[],"type":[]}
         * adcode : 410305
         * street : []
         * number : []
         * location : 112.394552,34.644045
         * level : 兴趣点
         */

        private String formatted_address;
        private String country;
        private String province;
        private String citycode;
        private String city;
        private String district;
        private NeighborhoodBean neighborhood;
        private BuildingBean building;
        private String adcode;
        private String location;
        private String level;
        private List<?> township;
        private List<?> street;
        private List<?> number;

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCitycode() {
            return citycode;
        }

        public void setCitycode(String citycode) {
            this.citycode = citycode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public NeighborhoodBean getNeighborhood() {
            return neighborhood;
        }

        public void setNeighborhood(NeighborhoodBean neighborhood) {
            this.neighborhood = neighborhood;
        }

        public BuildingBean getBuilding() {
            return building;
        }

        public void setBuilding(BuildingBean building) {
            this.building = building;
        }

        public String getAdcode() {
            return adcode;
        }

        public void setAdcode(String adcode) {
            this.adcode = adcode;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public List<?> getTownship() {
            return township;
        }

        public void setTownship(List<?> township) {
            this.township = township;
        }

        public List<?> getStreet() {
            return street;
        }

        public void setStreet(List<?> street) {
            this.street = street;
        }

        public List<?> getNumber() {
            return number;
        }

        public void setNumber(List<?> number) {
            this.number = number;
        }

        public static class NeighborhoodBean {
            private List<?> name;
            private List<?> type;

            public List<?> getName() {
                return name;
            }

            public void setName(List<?> name) {
                this.name = name;
            }

            public List<?> getType() {
                return type;
            }

            public void setType(List<?> type) {
                this.type = type;
            }
        }

        public static class BuildingBean {
            private List<?> name;
            private List<?> type;

            public List<?> getName() {
                return name;
            }

            public void setName(List<?> name) {
                this.name = name;
            }

            public List<?> getType() {
                return type;
            }

            public void setType(List<?> type) {
                this.type = type;
            }
        }
    }
}
