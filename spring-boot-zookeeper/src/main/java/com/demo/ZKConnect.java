package com.demo;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/08/15
 */
public class ZKConnect implements Watcher {

    public static final String zkServerPath = "192.168.152.128:2181";
    public static final Integer timeout = 60000;
    //集群下
    //public static final String zkServerPath ="192.168.1.111:2181,192.168.1.111:2182,192.168.1.111:2183";
    private final static Logger log = LoggerFactory.getLogger(ZKConnect.class);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException, NoSuchAlgorithmException {
        /**
         * 参数说明:
         * connectString, sessionTimeout, watcher, sessionId, sessionPasswd,canBeReadOnly
         *
         * connectString: 连接服务器的ip字符串，比如: "192.168.1.1:2181,192.168.1.2:2181,192.168.1.3:2181"
         * 可以是一个ip，也可以是多个ip，一个ip代表单机，多个ip代表集群,也可以在ip后加路径
         * sessionTimeout：超时时间，心跳收不到了，那就超时
         * watcher：通知事件，如果有对应的事件触发，则会收到一个通知，实现implements Watcher接口；如果不需要，那就设置为null。
         * canBeReadOnly：可读，当这个物理机节点断开后，还是可以读到数据的，只是不能写，
         *	此时数据被读取到的可能是旧数据，此处建议设置为false，不推荐使用
         * sessionId：会话的id
         * sessionPasswd：会话密码	当会话丢失后，可以依据 sessionId 和 sessionPasswd 重新获取会话
         *
         */
        ZooKeeper zk = new ZooKeeper(zkServerPath, timeout, (WatchedEvent watchedEvent) -> {
            log.warn("接受到watch通知：{}", watchedEvent);
        });
        log.warn("客户端开始连接zk服务端");
        log.warn("连接状态:{}", zk.getState());

        zk.exists("/lin", new ZKConnect());

        //同步创建，timeout 不能太短
        zk.create("/lin", "qin".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zk.setData("/lin", "1234".getBytes(), 0);

        //自定义用户认证访问
//        List<ACL> acls = new ArrayList<>();
//        Id lin = new Id("digest", DigestAuthenticationProvider.generateDigest("test:test"));
//        acls.add(new ACL(ZooDefs.Perms.ALL, lin));
//        zk.create("/lin", "qin".getBytes(), acls, CreateMode.PERSISTENT);
//        zk.addAuthInfo("digest", "test:test".getBytes());

//        List<ACL> aclsIP = new ArrayList<>();
//        Id ipId1 = new Id("ip", "127.0.0.1");
//        Id ipId2 = new Id("ip", "192.168.152.1");
//        aclsIP.add(new ACL(ZooDefs.Perms.ALL, ipId1));
//        aclsIP.add(new ACL(ZooDefs.Perms.ALL, ipId2));
//        zk.create("/lin/testIp", "testIp".getBytes(), aclsIP, CreateMode.EPHEMERAL);


        Thread.sleep(2000);

        //删除,设置监控点
        if (zk.exists("/lin", new ZKConnect()) != null) {
//            zk.delete("/lin", 1);
//        }

            // 获取节点信息
            byte[] data = zk.getData("/lin", true, new Stat());
            String result = new String(data);
            System.out.println("当前值:" + result);
//
//        List<String> children = zk.getChildren("/", true, new Stat());
//        children.forEach(System.out::println);

            // 异步创建
//        String ctx = "{'create':'success'}";
//        zk.create("/worke_1", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new CreateStringCallBack(), "创建");


            // 重连
//        long sessionId = zk.getSessionId();
//        String ssid = "0x" + Long.toHexString(sessionId);
//        log.warn("sessionId:{}", ssid);
//        byte[] sessionPasswd = zk.getSessionPasswd();
//        log.warn("开始会话重连...");
//        ZooKeeper zooKeeper = new ZooKeeper(zkServerPath,
//                timeout,
//                new ZKConnect(),
//                sessionId,
//                sessionPasswd);

            System.in.read();
        }
    }

    public void process(WatchedEvent event) {
        log.warn("接受到watch通知：{}", event);
        Event.EventType type = event.getType();
        switch (type) {
            case NodeCreated:
                System.out.println("新建节点:" + event.getPath());
            case NodeDeleted:
                System.out.println("删除节点:" + event.getPath());
            case NodeDataChanged:
                System.out.println("修改节点:" + event.getPath());
            case NodeChildrenChanged:
                System.out.println("子节点:" + event);
            case None:
                System.out.println("none");
        }

    }
}


class CreateStringCallBack implements AsyncCallback.StringCallback {
    /**
     * @param i  i<0创建失败 i=0 创建成功
     * @param s  节点名称
     * @param o  传入的上下文
     * @param s1 节点名称
     */
    public void processResult(int i, String s, Object o, String s1) {
        System.out.println(s);

    }
}