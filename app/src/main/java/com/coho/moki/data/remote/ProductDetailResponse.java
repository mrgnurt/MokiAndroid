package com.coho.moki.data.remote;

import java.util.List;

/**
 * Created by Khanh Nguyen on 11/3/2017.
 */

public class ProductDetailResponse {

    private String id;
    private String name;
    private Integer price;
    private Integer pricePercent;
    private String described;
    private String shipsFrom;
    private String condition;
    private String created;
    private Integer like;
    private Integer comment;
    private Integer isLiked;
    private List<Image> image;
    private List<Video> video;
    private List<Size> size;
    private List<Brand> brand;
    private Seller seller;
    private List<Category> category;
    private Integer isBlocked;
    private Integer canEdit;
    private Integer banned;
    private String url;
    private String weight;
    private Dimension dimension;

    public class Category {
        private String id;
        private String name;
        private Integer hasBrand;
        private Integer hasName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getHasBrand() {
            return hasBrand;
        }

        public void setHasBrand(Integer hasBrand) {
            this.hasBrand = hasBrand;
        }

        public Integer getHasName() {
            return hasName;
        }

        public void setHasName(Integer hasName) {
            this.hasName = hasName;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    public class Image {

        private String id;

        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "\n{" +
                    "\"url\": " + url +
                    "\n}";
        }
    }

    public class Brand {

        private String id;

        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class Size {
        private String id;

        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class Video {
        private String url;
        private String thumb;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        @Override
        public String toString() {
            return "{id: " + getUrl() + ", url: " + getThumb() + " } ";
        }
    }

    public class Seller {
        private String id;
        private String name;
        private String avatar;
        private Integer score;
        private Integer listing;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public Integer getListing() {
            return listing;
        }

        public void setListing(Integer listing) {
            this.listing = listing;
        }

        @Override
        public String toString() {
            return "{\n" +
                    "\"id\": " + id + ",\n" +
                    "\"avatar\": " + avatar + ",\n" +
                    "\"score\": " + score + ",\n" +
                    "\"listing\": " + listing +
                    "\n}";
        }
    }

    public class Dimension {
        private Integer length;
        private Integer width;
        private Integer height;

        public Integer getLength() {
            return length;
        }

        public void setLength(Integer length) {
            this.length = length;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        @Override
        public String toString() {
            return "\n{" +
                    "\"length\": " + length +
                    "\"width\": " + width +
                    "\"height\": " + height +
                    "\n}";
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPricePercent() {
        return pricePercent;
    }

    public void setPricePercent(Integer pricePercent) {
        this.pricePercent = pricePercent;
    }

    public String getDescribed() {
        return described;
    }

    public void setDescribed(String described) {
        this.described = described;
    }

    public String getShipsFrom() {
        return shipsFrom;
    }

    public void setShipsFrom(String shipsFrom) {
        this.shipsFrom = shipsFrom;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public Integer getLiked() {
        return isLiked;
    }

    public void setLiked(Integer liked) {
        isLiked = liked;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Integer getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Integer isLiked) {
        this.isLiked = isLiked;
    }

    public Integer getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Integer isBlocked) {
        this.isBlocked = isBlocked;
    }

    public Integer getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Integer canEdit) {
        this.canEdit = canEdit;
    }

    public Integer getBanned() {
        return banned;
    }

    public void setBanned(Integer banned) {
        this.banned = banned;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public List<Video> getVideo() {
        return video;
    }

    public void setVideo(List<Video> video) {
        this.video = video;
    }

    public List<Size> getSize() {
        return size;
    }

    public void setSize(List<Size> size) {
        this.size = size;
    }

    public List<Brand> getBrand() {
        return brand;
    }

    public void setBrand(List<Brand> brand) {
        this.brand = brand;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\"id\": " + id + ",\n " +
                "\"price\": " + price + ",\n " +
                "\"pricePercent\": " + pricePercent + ",\n" +
                "\"described\": " + described + ",\n" +
                "\"shipsFrom\": " + shipsFrom + ",\n" +
                "\"condition\": " + condition + ",\n" +
                "\"created\": " + created + ",\n" +
                "\"like\": " + like + ",\n" +
                "\"comment\": " + comment + ",\n" +
                "\"comment\": " + like + ",\n" +
                "\"isLiked\": " + isLiked + ",\n" +
                "\"image\": " + image.size() + ",\n" +
                "\"video\": " + video.size() + ",\n" +
                "\"size\": " + size.size() + ",\n" +
                "\"seller\": " + seller.toString() + ",\n" +
                "\"category\": " + category.toString() + ",\n" +
                "\"canEdit\": " + canEdit + ",\n" +
                "\"banned\": " + banned + ",\n" +
                "\"url\": " + url + ",\n" +
                "\"weight\": " + weight + ",\n" +
                "\"dimension\": " + dimension.toString() +
                "\n}";
    }

}
