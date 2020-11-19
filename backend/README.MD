PO Persistant Object, 用于存放数据到数据库  
VO View Object, 用于前端展示  
BO Business Object, 表示业务对象，用于Service和上层的转换

实体类有Department（部门）、Employee（雇员）、Goods（商品）、Position（位置）、Project（项目）
对应关系是
一个Employee对应一个Department
一个Goods对应一个或者零个Employee
一个Department有多个Employee
一个Employee持有多个Goods
一个Project对应多个Goods
一个Position对应多个Goods