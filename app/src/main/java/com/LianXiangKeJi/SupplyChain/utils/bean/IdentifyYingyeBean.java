package com.LianXiangKeJi.SupplyChain.utils.bean;

public class IdentifyYingyeBean {

    /**
     * log_id : 490058765
     * words_result : {"单位名称":{"location":{"left":500,"top":479,"width":618,"height":54},"words":"袁氏财团有限公司"},"类型":{"location":{"left":53,"top":64,"width":74,"height":97},"words":"有限责任公司（自然人独资）"},"法人":{"location":{"left":938,"top":557,"width":94,"height":46},"words":"袁运筹"},"地址":{"location":{"left":503,"top":644,"width":574,"height":57},"words":"江苏省南京市中山东路19号"},"有效期":{"location":{"left":779,"top":1108,"width":271,"height":49},"words":"2015年02月12日"},"证件编号":{"location":{"left":1219,"top":357,"width":466,"height":39},"words":"苏餐证字(2019)第666602666661号"},"社会信用代码":{"location":{"left":0,"top":0,"width":0,"height":0},"words":"无"}}
     * words_result_num : 6
     */

    private int log_id;
    private WordsResultBean words_result;
    private int words_result_num;

    public int getLog_id() {
        return log_id;
    }

    public void setLog_id(int log_id) {
        this.log_id = log_id;
    }

    public WordsResultBean getWords_result() {
        return words_result;
    }

    public void setWords_result(WordsResultBean words_result) {
        this.words_result = words_result;
    }

    public int getWords_result_num() {
        return words_result_num;
    }

    public void setWords_result_num(int words_result_num) {
        this.words_result_num = words_result_num;
    }

    public static class WordsResultBean {
        /**
         * 单位名称 : {"location":{"left":500,"top":479,"width":618,"height":54},"words":"袁氏财团有限公司"}
         * 类型 : {"location":{"left":53,"top":64,"width":74,"height":97},"words":"有限责任公司（自然人独资）"}
         * 法人 : {"location":{"left":938,"top":557,"width":94,"height":46},"words":"袁运筹"}
         * 地址 : {"location":{"left":503,"top":644,"width":574,"height":57},"words":"江苏省南京市中山东路19号"}
         * 有效期 : {"location":{"left":779,"top":1108,"width":271,"height":49},"words":"2015年02月12日"}
         * 证件编号 : {"location":{"left":1219,"top":357,"width":466,"height":39},"words":"苏餐证字(2019)第666602666661号"}
         * 社会信用代码 : {"location":{"left":0,"top":0,"width":0,"height":0},"words":"无"}
         */

        private 单位名称Bean 单位名称;
        private 类型Bean 类型;
        private 法人Bean 法人;
        private 地址Bean 地址;
        private 有效期Bean 有效期;
        private 证件编号Bean 证件编号;
        private 社会信用代码Bean 社会信用代码;

        public 单位名称Bean get单位名称() {
            return 单位名称;
        }

        public void set单位名称(单位名称Bean 单位名称) {
            this.单位名称 = 单位名称;
        }

        public 类型Bean get类型() {
            return 类型;
        }

        public void set类型(类型Bean 类型) {
            this.类型 = 类型;
        }

        public 法人Bean get法人() {
            return 法人;
        }

        public void set法人(法人Bean 法人) {
            this.法人 = 法人;
        }

        public 地址Bean get地址() {
            return 地址;
        }

        public void set地址(地址Bean 地址) {
            this.地址 = 地址;
        }

        public 有效期Bean get有效期() {
            return 有效期;
        }

        public void set有效期(有效期Bean 有效期) {
            this.有效期 = 有效期;
        }

        public 证件编号Bean get证件编号() {
            return 证件编号;
        }

        public void set证件编号(证件编号Bean 证件编号) {
            this.证件编号 = 证件编号;
        }

        public 社会信用代码Bean get社会信用代码() {
            return 社会信用代码;
        }

        public void set社会信用代码(社会信用代码Bean 社会信用代码) {
            this.社会信用代码 = 社会信用代码;
        }

        public static class 单位名称Bean {
            /**
             * location : {"left":500,"top":479,"width":618,"height":54}
             * words : 袁氏财团有限公司
             */

            private LocationBean location;
            private String words;

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public static class LocationBean {
                /**
                 * left : 500
                 * top : 479
                 * width : 618
                 * height : 54
                 */

                private int left;
                private int top;
                private int width;
                private int height;

                public int getLeft() {
                    return left;
                }

                public void setLeft(int left) {
                    this.left = left;
                }

                public int getTop() {
                    return top;
                }

                public void setTop(int top) {
                    this.top = top;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }
            }
        }

        public static class 类型Bean {
            /**
             * location : {"left":53,"top":64,"width":74,"height":97}
             * words : 有限责任公司（自然人独资）
             */

            private LocationBeanX location;
            private String words;

            public LocationBeanX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanX location) {
                this.location = location;
            }

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public static class LocationBeanX {
                /**
                 * left : 53
                 * top : 64
                 * width : 74
                 * height : 97
                 */

                private int left;
                private int top;
                private int width;
                private int height;

                public int getLeft() {
                    return left;
                }

                public void setLeft(int left) {
                    this.left = left;
                }

                public int getTop() {
                    return top;
                }

                public void setTop(int top) {
                    this.top = top;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }
            }
        }

        public static class 法人Bean {
            /**
             * location : {"left":938,"top":557,"width":94,"height":46}
             * words : 袁运筹
             */

            private LocationBeanXX location;
            private String words;

            public LocationBeanXX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanXX location) {
                this.location = location;
            }

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public static class LocationBeanXX {
                /**
                 * left : 938
                 * top : 557
                 * width : 94
                 * height : 46
                 */

                private int left;
                private int top;
                private int width;
                private int height;

                public int getLeft() {
                    return left;
                }

                public void setLeft(int left) {
                    this.left = left;
                }

                public int getTop() {
                    return top;
                }

                public void setTop(int top) {
                    this.top = top;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }
            }
        }

        public static class 地址Bean {
            /**
             * location : {"left":503,"top":644,"width":574,"height":57}
             * words : 江苏省南京市中山东路19号
             */

            private LocationBeanXXX location;
            private String words;

            public LocationBeanXXX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanXXX location) {
                this.location = location;
            }

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public static class LocationBeanXXX {
                /**
                 * left : 503
                 * top : 644
                 * width : 574
                 * height : 57
                 */

                private int left;
                private int top;
                private int width;
                private int height;

                public int getLeft() {
                    return left;
                }

                public void setLeft(int left) {
                    this.left = left;
                }

                public int getTop() {
                    return top;
                }

                public void setTop(int top) {
                    this.top = top;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }
            }
        }

        public static class 有效期Bean {
            /**
             * location : {"left":779,"top":1108,"width":271,"height":49}
             * words : 2015年02月12日
             */

            private LocationBeanXXXX location;
            private String words;

            public LocationBeanXXXX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanXXXX location) {
                this.location = location;
            }

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public static class LocationBeanXXXX {
                /**
                 * left : 779
                 * top : 1108
                 * width : 271
                 * height : 49
                 */

                private int left;
                private int top;
                private int width;
                private int height;

                public int getLeft() {
                    return left;
                }

                public void setLeft(int left) {
                    this.left = left;
                }

                public int getTop() {
                    return top;
                }

                public void setTop(int top) {
                    this.top = top;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }
            }
        }

        public static class 证件编号Bean {
            /**
             * location : {"left":1219,"top":357,"width":466,"height":39}
             * words : 苏餐证字(2019)第666602666661号
             */

            private LocationBeanXXXXX location;
            private String words;

            public LocationBeanXXXXX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanXXXXX location) {
                this.location = location;
            }

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public static class LocationBeanXXXXX {
                /**
                 * left : 1219
                 * top : 357
                 * width : 466
                 * height : 39
                 */

                private int left;
                private int top;
                private int width;
                private int height;

                public int getLeft() {
                    return left;
                }

                public void setLeft(int left) {
                    this.left = left;
                }

                public int getTop() {
                    return top;
                }

                public void setTop(int top) {
                    this.top = top;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }
            }
        }

        public static class 社会信用代码Bean {
            /**
             * location : {"left":0,"top":0,"width":0,"height":0}
             * words : 无
             */

            private LocationBeanXXXXXX location;
            private String words;

            public LocationBeanXXXXXX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanXXXXXX location) {
                this.location = location;
            }

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public static class LocationBeanXXXXXX {
                /**
                 * left : 0
                 * top : 0
                 * width : 0
                 * height : 0
                 */

                private int left;
                private int top;
                private int width;
                private int height;

                public int getLeft() {
                    return left;
                }

                public void setLeft(int left) {
                    this.left = left;
                }

                public int getTop() {
                    return top;
                }

                public void setTop(int top) {
                    this.top = top;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }
            }
        }
    }
}
