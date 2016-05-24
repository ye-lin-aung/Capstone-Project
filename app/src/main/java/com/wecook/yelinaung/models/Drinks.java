
package com.wecook.yelinaung.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class Drinks {

    @SerializedName("result")
    @Expose
    private List<Result> result = new ArrayList<Result>();
    @SerializedName("totalResult")
    @Expose
    private Integer totalResult;
    @SerializedName("next")
    @Expose
    private String next;

    /**
     * 
     * @return
     *     The result
     */
    public List<Result> getResult() {
        return result;
    }

    /**
     * 
     * @param result
     *     The result
     */
    public void setResult(List<Result> result) {
        this.result = result;
    }

    /**
     * 
     * @return
     *     The totalResult
     */
    public Integer getTotalResult() {
        return totalResult;
    }

    /**
     * 
     * @param totalResult
     *     The totalResult
     */
    public void setTotalResult(Integer totalResult) {
        this.totalResult = totalResult;
    }

    /**
     * 
     * @return
     *     The next
     */
    public String getNext() {
        return next;
    }

    /**
     * 
     * @param next
     *     The next
     */
    public void setNext(String next) {
        this.next = next;
    }

}
