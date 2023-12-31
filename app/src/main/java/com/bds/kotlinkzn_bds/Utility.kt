package com.bds.kotlinkzn_bds

import java.util.ArrayList

object Utility {

    private var shoppingItems: MutableList<Product> = ArrayList()

    private var eventItems: MutableList<Event> = ArrayList()

    private var signImage: String? = null


    init {
        if (shoppingItems.isEmpty()) {
            shoppingItems = ArrayList()
        }
        if (eventItems.isEmpty()) {
            eventItems = ArrayList()
        }

    }

    fun getShoppingItems(): List<Product> {
        return shoppingItems
    }

    fun setShoppingItems(shoppingItems: List<Product>) {
        Utility.shoppingItems = shoppingItems.toMutableList()
    }

    fun getEventItems(): List<Event> {
        return eventItems
    }

    fun setEventItems(eventItems: List<Event>?) {
        Utility.eventItems = eventItems!!.toMutableList()
    }

    fun getImage(): String {
        return signImage!!
    }

    fun setImage(image: String) {
        signImage = image
    }
}
