package com.newcoder.community.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yt
 * date 2022-07-10
 */

@Component
public class SensitiveFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    //替换符
    private static final String REPLACEMENT="***";

    //根节点
    private TireNode rootNode = new TireNode();

    //当容器调用这个bean的构造函数后自动调用这个init方法
    @PostConstruct
    public void init(){
        try(
                InputStream is = this.getClass().getClassLoader()
                        .getResourceAsStream("sensitive-words.txt");//获取target/classes目录下的文件
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ) {
            String keyword;
            while((keyword = reader.readLine())!=null){
                //添加到前缀树
                this.addKeyword(keyword);
            }
        } catch (IOException e) {
            logger.error("加载敏感词文件失败： "+e);
        }

    }

    private void addKeyword(String keyword){
        TireNode tempNode = rootNode;
        for(int i=0;i<keyword.length();i++){
            char c = keyword.charAt(i);
            TireNode subNode = tempNode.getSubNode(c);
            if(subNode == null){
                //初始化子结点
                subNode = new TireNode();
                tempNode.addSubNode(c,subNode);
            }
            tempNode = subNode;
            //设置结束标识
            if(i == keyword.length()-1){
                tempNode.setKeywordEnd(true);
            }
        }
    }

    /**
     * 过滤敏感词
     * @param text 待过滤敏感词的文本
     * @return 过滤后的
     */

    public String filter(String text){
        if(StringUtils.isBlank(text)){
            return null;
        }
        //指针1，在trie树上移动
        TireNode tempNode = rootNode;
        //指针2，敏感词开头
        int begin = 0;
        //指针3,文本上移动，指针2后面
        int pos=0;
        StringBuilder sb = new StringBuilder();

        while (begin < text.length()){
            if(pos < text.length()) {
                char c = text.charAt(pos);
                //跳过符号
                if (isSymbol(c)) {
                    //若指针1处于根节点,将此符号计入结果，让指针2向下走一步
                    if (tempNode == rootNode) {
                        sb.append(c);
                        begin++;
                    }
                    pos++;
                    continue;
                }
                //检查下级结点
                tempNode = tempNode.getSubNode(c);
                if (tempNode == null) {
                    //以begin开头的字符串不是敏感词
                    sb.append(text.charAt(begin));
                    //进入下一个位置
                    pos = ++begin;
                    //重新指向根节点
                    tempNode = rootNode;
                } else if (tempNode.isKeywordEnd()) {
                    //发现敏感词，替换掉
                    sb.append(REPLACEMENT);
                    begin = ++pos;
                    tempNode = rootNode;
                } else {
                    pos++;
                }
            }
            //pos越界仍未匹配到敏感词
            else {
                sb.append(text.charAt(begin));
                pos = ++begin;
                tempNode = rootNode;
            }
        }
        //将最后一批字符计入结果
      //  sb.append(text.substring(begin));
        return sb.toString();
    }

    //判断是否为符号
    private boolean isSymbol(Character c){
        //0x2E80~0x9FFF是东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }


    //前缀树
    private class TireNode{
        //关键词结束的标识
        private boolean isKeywordEnd = false;

        //子结点(key 是下级字符，value是下级结点)
        private Map<Character,TireNode> subNodes = new HashMap<>();

        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }

        //添加子结点
        public void  addSubNode(Character c,TireNode node){
            subNodes.put(c,node);
        }

        //获取子结点
        public TireNode getSubNode(Character c){
            return subNodes.get(c);
        }
    }
}
