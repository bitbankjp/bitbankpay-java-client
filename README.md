bitbankpay-java-client
=======================

Examples
--------

### createInvoice

```java
BitbankPay bitbankPay = new BitbankPay("API Key");
bitbankPay.setOrderID("order_id");
bitbankPay.setUserMail("mail");
String json = bitbankPay.createInvoice(amount,"currency","item_name");
```

### acceptJpyYen

```java
Object obj = JSONValue.parse(json);
JSONObject jsonObj = (JSONObject)obj;
String id = (String)jsonObj.get("id");
bitbankPay.acceptJpyYen(id);
```
### acceptBitcoin

```java
Object obj = JSONValue.parse(json);
JSONObject jsonObj = (JSONObject)obj;
String id = (String)jsonObj.get("id");
bitbankPay.acceptBitcoin(id);
```