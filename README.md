# NDSCNoResponse
The demo of why calling notifyDataSetChanged is not work for RecyclerView or ListView
--------------------
今天在项目中发现怎么使用notifyDataSetChanged刷新数据都没用，千看百看，忽然灵感一来，记得以前也有这样的情况，赶紧记录在案以防我的笨脑瓜子又忘了。

> 关键点：notifyDataSetChanged只负责对你第一次设置的数据源进行“监听”。假如在中途你的数据源地址（引用）发生了改变，再去调用notifyDataSetChanged将不会生效。


# 错误使用场景（逻辑）
````
/*createNewData返回新生成的数据数组*/
ArrayList<String> createNewData() {
    ArrayList<String> newData = new ArrayList<>();
    get data;
    return newData;
}
//...
ArrayList<String> mData;
//...
mData = createNewData();
mAdapter = new Adapter(mData);
mRecyclerView.setAdapter(mAdapter);

void onClick {
    updateData();
}

void updateData() {
    mData = createNewData();
    mAdapter.notifyDataSetChanged();
}
````

在上述情况下，当需要重新生成数据的时候，再次调用createNewData，notifyDataSetChanged是不可能起作用的。
因为mData的地址引用的list已经不是第一次传给Adapter的那个mData的引用了。

# 正确使用场景（逻辑）
````
/*createNewData返回新生成的数据数组*/
ArrayList<String> createNewData() {
    ArrayList<String> newData = new ArrayList<>();
    get data;
    return newData;
}
//...
ArrayList<String> mData;
//...
mData = new ArrayList<>();
mData.addAll(createNewData());
mAdapter = new Adapter(mData);
mRecyclerView.setAdapter(mAdapter);

void onClick {
    updateData();
}

void updateData() {
    mData.clear();
    mData.addAll(createNewData());
    mAdapter.notifyDataSetChanged();
}
````
而在这种情况下，因为mData的地址已经固定，只是对其内容进行增删，通过调用notifyDataSetChanged则可以判断mData是否发生了变化从而刷新UI。


# DEMO
最后附上[demo地址](https://github.com/Bravest-Ptt/NDSCNoResponse)， 简单展现了这两种情况。
![效果图](http://upload-images.jianshu.io/upload_images/3971774-c357e4ceffc99efa?imageMogr2/auto-orient/strip)
