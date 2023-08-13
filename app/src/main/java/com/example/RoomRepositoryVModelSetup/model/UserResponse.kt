package com.example.RoomRepositoryVModelSetup.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

 class UserResponse {
    @SerializedName("page")
    @Expose
    private var page: Int? = null

    @SerializedName("per_page")
    @Expose
    private var perPage: Int? = null

    @SerializedName("total")
    @Expose
    private var total: Int? = null

    @SerializedName("total_pages")
    @Expose
    private var totalPages: Int? = null

    @SerializedName("data")
    @Expose
    private var data: List<Datum?>? = null

    @SerializedName("support")
    @Expose
    private var support: Support? = null

    fun getPage(): Int? {
        return page
    }

    fun setPage(page: Int?) {
        this.page = page
    }

    fun getPerPage(): Int? {
        return perPage
    }

    fun setPerPage(perPage: Int?) {
        this.perPage = perPage
    }

    fun getTotal(): Int? {
        return total
    }

    fun setTotal(total: Int?) {
        this.total = total
    }

    fun getTotalPages(): Int? {
        return totalPages
    }

    fun setTotalPages(totalPages: Int?) {
        this.totalPages = totalPages
    }

    fun getData(): List<Datum?>? {
        return data
    }

    fun setData(data: List<Datum?>?) {
        this.data = data
    }

    fun getSupport(): Support? {
        return support
    }

    fun setSupport(support: Support?) {
        this.support = support
    }



    class Support {
        @SerializedName("url")
        @Expose
        var url: String? = null

        @SerializedName("text")
        @Expose
        var text: String? = null

    }
}