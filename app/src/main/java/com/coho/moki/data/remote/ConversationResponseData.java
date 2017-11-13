package com.coho.moki.data.remote;

import java.util.List;

/**
 * Created by tonquangtu on 14/11/2017.
 */

public class ConversationResponseData {

    private List<Conversation> conversation;

    private ProductCons product;

    public ConversationResponseData(List<Conversation> conversation, ProductCons product) {
        this.conversation = conversation;
        this.product = product;
    }

    public List<Conversation> getConversation() {
        return conversation;
    }

    public void setConversation(List<Conversation> conversation) {
        this.conversation = conversation;
    }

    public ProductCons getProduct() {
        return product;
    }

    public void setProduct(ProductCons product) {
        this.product = product;
    }
}
