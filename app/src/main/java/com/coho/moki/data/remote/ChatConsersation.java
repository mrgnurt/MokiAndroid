package com.coho.moki.data.remote;

import com.coho.moki.data.model.Product;

/**
 * Created by trung on 14/11/2017.
 */

public class ChatConsersation {

        public String id;
        public PartnerChat partner;
        public ProductChat product;
        public LastMessageChat lastMessage;

        public ChatConsersation(String id, PartnerChat partner, ProductChat product, LastMessageChat lastMessage) {
            this.id = id;
            this.partner = partner;
            this.product = product;
            this.lastMessage = lastMessage;
        }
}
