package com.jiabb.springbootquartztest;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO {

    @Setter
    @Getter
    private String code;
    @Setter
    @Getter
    private Integer status;

    @Setter
    @Getter
    private Object data;

    public ResultDTO(String code,Integer status){
        this.code = code;
        this.status = status;
    }


    public static ResultDTO ofSuccess(){
        return new ResultDTO("success",200);
    }

    public static ResultDTO ofError(){
        return new ResultDTO("error",500);
    }
}
