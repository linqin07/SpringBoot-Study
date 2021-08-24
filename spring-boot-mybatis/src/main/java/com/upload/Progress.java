package com.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/08/02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Progress {
    private long bytesRead; //已读取文件的比特数
    private long contentLength;//文件总比特数
    private long items; //正读的第几个文件
}
