package com.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@Data
public class EventBean {
    private int type;
    private K8sTsfBean k8sTsf;

    @NoArgsConstructor
    @Data
    public class K8sTsfBean {
        private int id;
        private List<VolumesBean> volumes;

        @NoArgsConstructor
        @Data
        public class VolumesBean {
            private String name;
            private String hostPath;
        }
    }
}

