/*
 * Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dao;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Generates entities and DAOs for the example project DaoExample.
 * <p/>
 * Run it as a Java application (not Android).
 *
 * @author Markus
 */
public class ExampleDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.yurc.customerbus.dao");

        //创建表
//        addShoppingCart(schema);//购物车表

//        addAnswerRemind(schema);//答题提醒表
        addBusLine(schema);//
        new DaoGenerator().generateAll(schema, "app/src/main/java");
    }

    /**
     * 创建购物车类
     */
    private static void addShoppingCart(Schema schema) {
        //创建表
        Entity entity = schema.addEntity("ShoppingCart");
        //设置自增ID为主键
        entity.addIdProperty().primaryKey().autoincrement();
        //商品数量
        entity.addIntProperty("goodsCount");
        //商品ID
        entity.addIntProperty("goodsId");
        //商品名称
        entity.addStringProperty("goodsName");
        //商品图片
        entity.addStringProperty("goodsPic");
        //商品价格
        entity.addFloatProperty("goodsPrice");
        //商品标题
        entity.addStringProperty("goodsTitle");
        //商品规格
        entity.addStringProperty("standard");
        //是否选择
        entity.addBooleanProperty("choose");
    }
    /**
     * 创建答题提醒类
     * */
    private static void addAnswerRemind(Schema schema){
        //创建表
        Entity entity = schema.addEntity("AnswerRemind");
        //设置主键为自增
        entity.addIdProperty().primaryKey().autoincrement();
        //时数
        entity.addIntProperty("hour");
        //分钟数
        entity.addIntProperty("minute");
        //描述字段
        entity.addStringProperty("declare");
        //提醒开关状态
        entity.addBooleanProperty("status");
        //重复状态
        entity.addBooleanProperty("repeat");
        //重复日期
        entity.addStringProperty("repeatDate");
        //是否震动字段
        entity.addBooleanProperty("vibration");
        //是否响铃
        entity.addBooleanProperty("bell");
    }

    public static void addBusLine(Schema schema){
        Entity entity = schema.addEntity("BusLine");
        //设置主键为自增
        entity.addIdProperty().primaryKey().autoincrement();
        //时数
        entity.addStringProperty("StartTime");
        //分钟数
        entity.addStringProperty("EndTime");
        //线路名称
        entity.addStringProperty("linename");
        //最近的站点
        entity.addStringProperty("nearlySite");
        //距离
        entity.addStringProperty("distance");
        //方向站点
        entity.addStringProperty("directionStie");

    }

}
