package com.bds.kotlinkzn_bds

class InvoicePayments(
    private var siteReference: String,
    private var currencyISOCode: String,
    private var amount: Int,
    private var origin: String,
    private var createdUTCDate: String,
    private var originReference: String,
    private var reference: String,
    private var state: String
) {

    fun getSiteReference(): String {
        return siteReference
    }

    fun setSiteReference(siteReference: String) {
        this.siteReference = siteReference
    }

    fun getCurrencyISOCode(): String {
        return currencyISOCode
    }

    fun setCurrencyISOCode(currencyISOCode: String) {
        this.currencyISOCode = currencyISOCode
    }

    fun getAmount(): Int {
        return amount
    }

    fun setAmount(amount: Int) {
        this.amount = amount
    }

    fun getOrigin(): String {
        return origin
    }

    fun setOrigin(origin: String) {
        this.origin = origin
    }

    fun getCreatedUTCDate(): String {
        return createdUTCDate
    }

    fun setCreatedUTCDate(createdUTCDate: String) {
        this.createdUTCDate = createdUTCDate
    }

    fun getOriginReference(): String {
        return originReference
    }

    fun setOriginReference(originReference: String) {
        this.originReference = originReference
    }

    fun getReference(): String {
        return reference
    }

    fun setReference(reference: String) {
        this.reference = reference
    }

    fun getState(): String {
        return state
    }

    fun setState(state: String) {
        this.state = state
    }

    class LineItem(
        private var name: String,
        private var productCode: String,
        private var SKU: String,
        private var unitPrice: Int,
        private var categories: List<String>,
        private var quantity: Int
    ) {

        fun getName(): String {
            return name
        }

        fun setName(name: String) {
            this.name = name
        }

        fun getProductCode(): String {
            return productCode
        }

        fun setProductCode(productCode: String) {
            this.productCode = productCode
        }

        fun getSKU(): String {
            return SKU
        }

        fun setSKU(SKU: String) {
            this.SKU = SKU
        }

        fun getUnitPrice(): Int {
            return unitPrice
        }

        fun setUnitPrice(unitPrice: Int) {
            this.unitPrice = unitPrice
        }

        fun getCategories(): List<String> {
            return categories
        }

        fun setCategories(categories: List<String>) {
            this.categories = categories
        }

        fun getQuantity(): Int {
            return quantity
        }

        fun setQuantity(quantity: Int) {
            this.quantity = quantity
        }
    }
}
