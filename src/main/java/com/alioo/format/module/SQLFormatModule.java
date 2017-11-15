package com.alioo.format.module;


import com.alioo.format.sql.SQLDefaultFormatTool;
import com.alioo.format.sql.SQLInsertFormatTool;
import com.alioo.format.sql.SQLSelectFormatTool;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuzhichong on 2016/8/30.
 */
public class SQLFormatModule implements FormatModule {

    /**
     * @param str
     * @return
     */
    public String format(String str) {
        //判断sql类型
        String result = null;
        boolean isSelectSQL = isSelectSQL(str);
        if (isSelectSQL) {
            result = SQLSelectFormatTool.format(str);
            return result;
        }
        boolean isInsertSQL = isInsertSQL(str);
        if (isInsertSQL) {
            result = SQLInsertFormatTool.format(str);
            return result;
        }

        result = SQLDefaultFormatTool.format(str);
        return result;
    }

    private boolean isSelectSQL(String str) {
        if (str.toLowerCase().matches(".*select.*from .*")) {
            return true;
        }
        return false;
    }

    private boolean isInsertSQL(String str) {
        if (str.toLowerCase().matches("\\s*insert.+(values|select).+")) {
            return true;
        }
        return false;
    }


    /**
     * @param str
     * @return
     */
    public static String baseFormat(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        str = str.replaceAll("[\n\t]", " "); //去掉特殊符号
        str = str.replaceAll("\\s{2,}", " "); //同时含有多个空格的变成一个空格
        str = str.replaceAll("(,\\s+|\\s+,)", ","); //把逗号前后的空格去掉
        str = str.replaceAll("\\(\\s+", "("); //把"("后的空格去掉
        str = str.replaceAll("\\s+\\)", ")"); //把"("后的空格去掉
//        System.out.println("first=====\n" + str);

        return str;
    }
    public static void main(String[] args) {
        String str = null;
//        str = "CREATE TABLE `orderinfo_main_10` ( \n" +
//                "\t`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id' , \n" +
//                "\t`o2o_order_id` bigint(20) DEFAULT NULL COMMENT 'o2o订单 中心订单id', `order_id` varchar(100) NOT NULL COMMENT '订单id', `sorting_type` int(11) DEFAULT NULL COMMENT '分拣类型1商家,2用户', " +
//                "`vender_id` " +
//                "varchar" +
//                "(40) NOT NULL COMMENT '组织编号', `pop_vender_id` varchar(20) NOT NULL DEFAULT '' COMMENT 'pop商家id', `order_source` tinyint(4) NOT NULL DEFAULT '1' COMMENT '订单来源 1：京东lsp订单 2：京东医药城', `order_paid_time` datetime DEFAULT NULL COMMENT '在线支付订单的支付完成时间', `order_pre_start_delivery_time` datetime DEFAULT NULL COMMENT '预计送达开始时间', `order_pre_delivery_time` datetime DEFAULT NULL COMMENT '预计结束送达时间', `pay_type` tinyint(4) NOT NULL COMMENT '支付方式（1货到付款, 2邮局汇款, 3自提, 4在线支付, 5公司转账, 6银行转账 ）', `order_stock_owner` varchar(10) DEFAULT NULL COMMENT '库存归属 3: 未维护 4：生鲜仓 5：冷藏仓 6：冷冻仓', `order_total_price` decimal(12,2) NOT NULL COMMENT '订单总金额', `order_payment` decimal(12,2) NOT NULL COMMENT '用户应付金额', `station_order_payment` decimal(12,2) DEFAULT '0.00' COMMENT '门店应收金额', `order_seller_price` decimal(12,2) NOT NULL COMMENT '订单货款金额（订单总金额-商家优惠金额）', `seller_discount` decimal(12,2) DEFAULT NULL COMMENT '商家优惠金额', `order_jingdou_money` bigint(20) DEFAULT NULL COMMENT '京豆金额', `order_start_time` datetime NOT NULL COMMENT '下单时间', `receive_order_pin` varchar(50) DEFAULT NULL COMMENT '收单人', `receive_order_station_no` varchar(50) DEFAULT NULL COMMENT '收单门店编号', `receive_order_time` datetime DEFAULT NULL COMMENT '门店收单时间', `send_order_time` datetime DEFAULT NULL COMMENT '妥投时间', `fullname` varchar(100) NOT NULL COMMENT '收货人姓名', `full_address` varchar(500) DEFAULT NULL COMMENT '收货人地址', `telephone` varchar(20) DEFAULT NULL COMMENT '收货人电话', `mobile` varchar(20) DEFAULT NULL COMMENT '收货人手机号', `pin` varchar(100) NOT NULL COMMENT '即买家的账号信息', `station_no` varchar(100) NOT NULL COMMENT '配送门店编号', `station_name` varchar(50) NOT NULL COMMENT '站点名称', `station_no_isv` varchar(50) DEFAULT NULL COMMENT '站点编号（外部）', `station_order_type` int(11) DEFAULT NULL COMMENT '6（半小时达）、 9(1小时达)、 12（2小时达）、15（3小时达）、18（4小时达）、21（5小时达）、24（6小时达）、27（7小时达）、1（次日达）、2（自定义）、0（无时效)', `station_delivery_type` tinyint(4) DEFAULT NULL COMMENT '门店配送类型 2：送货上门；1：到站点自提', `delivery_type_item` tinyint(4) DEFAULT NULL COMMENT '配送类型：1京东配送 ，2：商家门店自送 3 第三方配送 4 自提', `take_self_code` varchar(20) DEFAULT NULL COMMENT '自提码', `station_payment_type` tinyint(4) DEFAULT NULL COMMENT '站点收款方式 4：在线支付；1：现金；2：POS刷卡；3：混合支付', `station_payment_cash` decimal(12,2) DEFAULT NULL COMMENT '站点实收现金', `station_payment_pos` decimal(12,2) DEFAULT NULL COMMENT '站点POS刷卡金额', `station_order_status` tinyint(4) NOT NULL COMMENT '门店订单状态 -1、返调度 -2、门店锁定 -3、前台锁定 -4、已取消 1、等待出库 2、已发货 3、站点收货 4、配送中 5、待自提 6、已妥投 20、等待上门服务 24、上门服务中 27、服务完成', `station_order_status_time` datetime DEFAULT NULL COMMENT '门店订单更新时间', `order_cancel_pin` varchar(100) DEFAULT NULL COMMENT '取消操作人pin', `order_cancel_time` datetime DEFAULT NULL COMMENT '订单取消时间', `order_cancel_source` int(11) DEFAULT NULL COMMENT '订单取消来源，1,客户取消；2，商家取消；3配送员取消；4风控取消；5客服取消', `order_cancel_reason` tinyint(4) DEFAULT NULL COMMENT '订单取消原因', `order_cancel_remark` varchar(200) DEFAULT NULL COMMENT '订单取消备注', `order_is_closed` tinyint(4) DEFAULT NULL COMMENT '订单是否关闭', `order_close_time` datetime DEFAULT NULL COMMENT '订单关闭时间', `adjust_version` int(11) DEFAULT NULL, `order_resend_reason` tinyint(4) DEFAULT NULL COMMENT '（废弃）申请再投原因', `order_resend_remark` varchar(200) DEFAULT NULL COMMENT '（废弃）申请再投备注', `resend_times` int(11) DEFAULT NULL COMMENT '（废弃）再投次数', `anti_dispatcher_remark` varchar(200) DEFAULT NULL COMMENT '反调度备注', `sendpay` varchar(500) DEFAULT NULL COMMENT 'sendpay', `order_biz_uuid` varchar(50) DEFAULT NULL COMMENT '订单业务处理唯一标识', `order_type` tinyint(4) DEFAULT NULL COMMENT '订单类型 Fbp：21 sop：22 Lbp：23 Sopl：25 LSP：77', `o2o_order_type` int(11) DEFAULT NULL COMMENT '订单中心订单类型', `business_type` tinyint(4) DEFAULT NULL COMMENT '所属业务', `order_sku_type` tinyint(4) DEFAULT '0' COMMENT '订单商品类型 0：其他 1：鲜食 ', `export_stock_type` varchar(10) DEFAULT NULL COMMENT '订单出库类型：1：库房到门店 2：库房到客户 3： 门店到客户', `order_search_remark` varchar(10) DEFAULT NULL COMMENT '订单查询备注 1：厂家直送拒收 2：厂家直送需退发货 3：厂家直送已退发货 10 : 门店实物订单取消审核需确认收货 11： 门店实物订单取消审核已经确认收货', `receive_pin` varchar(50) DEFAULT NULL COMMENT '收货人', `receive_time` datetime DEFAULT NULL COMMENT '收货时间', `invoice_state` varchar(10) DEFAULT NULL COMMENT '是否需要开具发票(0,1为需开，2,3为不需开)', `parcel_num` tinyint(4) DEFAULT '1' COMMENT '（废弃）包裹数，默认值为1', `parcel_weight` decimal(12,4) DEFAULT '0.0000' COMMENT '（废弃）商品重量', `print_mark` tinyint(4) DEFAULT '1' COMMENT '打印标识 1：未打印；2已打印', `print_first_time` datetime DEFAULT NULL COMMENT '打印时间', `pick_mark` tinyint(4) DEFAULT '1' COMMENT '拣货标识 1：未拣货，2：已拣货', `pick_time` datetime DEFAULT NULL COMMENT '打包时间', `pick_pin` varchar(50) DEFAULT NULL COMMENT '拣货人', `pick_time_range` bigint(20) DEFAULT NULL COMMENT '拣货用时', `grab_mark` tinyint(4) DEFAULT NULL COMMENT '抢单标示 1：待抢单 2：已抢单 3：已收单', `grab_time` datetime DEFAULT NULL COMMENT '抢单时间', `carrier_no` varchar(50) DEFAULT NULL COMMENT 'POP承运商编号', `carrier_name` varchar(50) DEFAULT NULL COMMENT '承运商名称', `seller_delivery_enable_time` datetime DEFAULT NULL COMMENT '允许商家自送时间', `third_carrier_no` varchar(50) DEFAULT NULL COMMENT '第三方配送承运商编号', `third_carrier_name` varchar(50) DEFAULT NULL COMMENT '第三方配送承运商名称', `deliver_no` varchar(50) DEFAULT NULL COMMENT '承运单号', `parent_order_id` varchar(50) DEFAULT NULL COMMENT '父订单号', `support_station_no` varchar(50) DEFAULT NULL COMMENT '支援门店编号', `support_station_name` varchar(50) DEFAULT NULL COMMENT '支援门店名称', `support_station_no_isv` varchar(50) DEFAULT NULL COMMENT '支援门店外部编号', `coord_type` tinyint(4) DEFAULT '2' COMMENT '坐标类型 1：phone 2:qq 3:google 4:baidu', `lat` double NOT NULL DEFAULT '0' COMMENT '用户坐标精度', `lng` double NOT NULL DEFAULT '0' COMMENT '用户坐标纬度', `sys_version` int(11) DEFAULT '0' COMMENT '数据版本号', `create_time` datetime DEFAULT NULL COMMENT 'create_time', `create_pin` varchar(50) DEFAULT NULL, `update_time` datetime DEFAULT NULL, `update_pin` varchar(50) DEFAULT NULL, `yn` tinyint(4) NOT NULL DEFAULT '0', `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, `industry_tag` tinyint(4) DEFAULT NULL COMMENT '门店行业标签', `src_inner_type` int(11) DEFAULT NULL COMMENT '售后单类型', `src_inner_order_id` bigint(11) DEFAULT NULL COMMENT '父订单号', `packaging_money` bigint(11) DEFAULT NULL COMMENT '餐盒费', `is_groupon` tinyint(4) DEFAULT '0' COMMENT '是否拼团订单', `accept_deadline` datetime DEFAULT NULL COMMENT '最后接单时间', PRIMARY KEY (`id`), UNIQUE KEY `idx_odm_oid` (`order_id`,`order_source`) USING BTREE, KEY `idx_o2o_order_id` (`o2o_order_id`) USING BTREE, KEY `idx_create_time` (`create_time`), KEY `idx_order_station_startTime` (`station_no`,`order_start_time`), KEY `idx_order_delvno` (`deliver_no`), KEY `idx_status_no_delivery_time` (`station_order_status`,`station_no`,`order_pre_delivery_time`) ) ENGINE=InnoDB AUTO_INCREMENT=598075 DEFAULT CHARSET=utf8 COMMENT='订单主表'\n";

//        str = "select\n" +
//                "         \n" +
//                "        id,orderId,orderStatus,orderStartTime,orderAgingType,buyerPin,jdPin,buyerFullName,buyerFullAddress,buyerMobile,\n" +
//                "        buyerProvince,buyerCity,produceStationNo,stationName,deliveryStationNo,deliveryType,deliveryPackageWeight,orderPayType,\n" +
//                "        orderTotalMoney,orderDiscountMoney,orderFreightMoney,orderGoodsMoney,orderBuyerPayable,orderInvoiceOpenMark,\n" +
//                "        orderInvoiceType,orderInvoiceTitle,orderInvoiceContent,orderBuyerRemark,orderSource,createTime,updateTime,yn,ts,\n" +
//                "        discountType,discountCode,discountPrice,deliveryTime,packagingCost,outOrderId,specialServiceTag,orderPlatform,orgCode,\n" +
//                "        sendTime,businessTag,invokeCount,ip,longit,dimen,deviceId,poi,countryId,coordType,elemCartId,orderOcsType,jingdouMoney,\n" +
//                "        orderOrgId,srcOrderId,afterSaleType,ordererName,ordererMobile,sync,faildCount,freightRuleCode,freightXml\n" +
//                "        ,totalReceivableMoney,goodsDiscountMoney,totalFreightDiscountMoney,totalDiscountMoney,carrierNo,cashReceiver,businessType,totalMealBoxMoney\n" +
//                "     \n" +
//                "        from order_main_pdj\n" +
//                "        where yn=0\n" +
//                "         \n" +
//                "         \n" +
//                "            and (invokeCount  <  20 or invokeCount is null )\n" +
//                "         \n" +
//                "\n" +
//                "        and mod(id,12) in (2)\n" +
//                "         \n" +
//                "        LIMIT 500;";

        str = "insert  into `bank_info`\n" +
                "(`Id`,`OperGuid`,`CreateTime`,`LastModTime`,`LogicalDel`,`Version`,`Abbreviation`,`Address`,`BankName`,`BankNameEn`,`BankNumber`,`Description`,`Status`,`Type`)\n" +
                "values\n" +
                "(NULL,'8727d831-9f93-11e3-9969-1260d495f62a','2014-06-28 01:43:55.000000',now(),'0',1403891035,'PSBC',NULL,'中国邮政储蓄银行','Postal Savings Bank of China',NULL,NULL,NULL,NULL),\n" +
                "(NULL,'8727d831-9f93-11e3-9969-1260d495f62a','2014-06-28 01:43:55.000000',now(),'0',1403891035,'CNCB',NULL,'中信银行','China CITIC Bank',NULL,NULL,NULL,NULL),\n" +
                "(NULL,'8727d831-9f93-11e3-9969-1260d495f62a','2014-06-28 01:43:55.000000',now(),'0',1403891035,'CMB',NULL,'招商银行','China Merchants Bank',NULL,NULL,NULL,NULL),\n" +
                "(NULL,'8727d831-9f93-11e3-9969-1260d495f62a','2014-06-28 01:43:55.000000',now(),'0',1403891035,'ABC',NULL,'中国农业银行','Agricultural Bank of China',NULL,NULL,NULL,NULL),\n" +
                "(NULL,'8727d831-9f93-11e3-9969-1260d495f62a','2014-06-28 01:43:55.000000',now(),'0',1403891035,'CCB',NULL,'中国建设银行','China Construction Bank',NULL,NULL,NULL,NULL),\n" +
                "(NULL,'8727d831-9f93-11e3-9969-1260d495f62a','2014-06-28 01:43:55.000000',now(),'0',1403891035,'ICBC',NULL,'中国工商银行','China Construction Bank',NULL,NULL,NULL,NULL);";
        str = baseFormat(str);
        String str2 = new SQLFormatModule().format(str);
        System.out.println("==================");
        System.out.println(str2);
    }


    private static void bb() {


//被替换关键字的的数据源
        Map<String, String> tokens = new HashMap<String, String>();
        tokens.put("cat", "Garfield");
        tokens.put("beverage", "coffee");

        //匹配类似velocity规则的字符串
        String template = "aaaa${cat} really needs some ${beverage}.";
        //生成匹配模式的正则表达式
//        String patternString = "\\$\\{(" + StringUtils.join(tokens.keySet(), "|") + ")\\}";
        String patternString = "\\$\\{(cat|beverage)\\}";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(template);

        //两个方法：appendReplacement, appendTail
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String aa = matcher.group(0);
            System.out.println(aa);
            aa = matcher.group(1);
            System.out.println(aa);
//            format=matcher.group(2);
//            System.out.println(format);

            matcher.appendReplacement(sb, tokens.get(matcher.group(1)));
            System.out.println("==========" + sb.toString());
        }
        matcher.appendTail(sb);

        //out: Garfield really needs some coffee.
        System.out.println(sb.toString());

    }

    public static void cc(String[] args) {

//获得匹配值
        String temp = "<meta-data android:name=\"appid\" android:value=\"joy\"></meta-data>";
        Pattern pattern = Pattern.compile("android:(name|value)=\"(.+?)\"");
        Matcher matcher = pattern.matcher(temp);
        while (matcher.find()) {
            System.out.println("matcher.groupCount();=" + matcher.groupCount());
            //out: appid, joy
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
        }
    }


}
