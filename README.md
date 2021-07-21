# d_bee_5
## 工程在登陆页面使用了room、rxjava、sharedpreferences
### 虽然一般会使用AsyncTask来操作room 这里使用使用room和rxjava的原因是因为学习了**没有写过** 刚好借此机会试试 中途也遇到了一些问题 通过上网查找解决了
## sharedpreferences
### 用于实现**记住密码**这一功能
## room
### 用于操作sqlite 实现注册功能 将账号密码存入数据库
## rxjava
### 数据库操作不能在UI线程中操作 （这里练手）
