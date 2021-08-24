使用nginx进行代理有加上配置

    proxy_connect_timeout 4s; 
    proxy_read_timeout 7200s; 
    proxy_send_timeout 12s; 
    proxy_set_header Upgrade $http_upgrade;
    proxy_set_header Connection "upgrade";
