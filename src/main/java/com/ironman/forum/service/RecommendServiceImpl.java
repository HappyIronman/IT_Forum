package com.ironman.forum.service;

import com.ironman.forum.conf.UserLoginUtil;
import com.ironman.forum.util.*;
import com.ironman.forum.vo.RecommendItemVO;
import com.ironman.forum.vo.RecommendResVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Log4j
public class RecommendServiceImpl implements RecommendService {

    /**
     * 首页推荐接口
     */
    @Value("#{prop.homepage_recommend_api}")
    private String homepageRecommendApi;

    /**
     * 推荐接口partnerId
     */
    @Value("#{prop.recommend_partner_id}")
    private String partnerId;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<RecommendItemVO> getRecommendList(PageRequest pageRequest) throws GlobalException {
        String userUniqueId = UserLoginUtil.getLoginUserUniqueId();
        List<RecommendItemVO> recommendItemVOList = new ArrayList<>();
        //限制最多5页
        if (pageRequest.getPage() > 4) {
            return recommendItemVOList;
        }
//        1.校验请求
//        2.调用推荐系统接口
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("partnerId", this.partnerId);
        params.add("userId", userUniqueId);
        params.add("page", pageRequest.getPage());
        params.add("size", pageRequest.getSize());
        RecommendResVO recommendResVO = restTemplate.postForObject(this.homepageRecommendApi,
                params, RecommendResVO.class);
        if (!recommendResVO.isSuccess()) {
            log.error(recommendResVO.getMsg());
            throw new GlobalException(ResponseStatus.SYSTEM_ERROR);
        }
        System.out.println(IronUtil.toJson(recommendResVO));
        List<Map> resultList = (List<Map>) recommendResVO.getMsg();
//        3.参数解析组装
        if (CollectionUtils.isEmpty(resultList)) {
            return recommendItemVOList;
        }
        for (Map<String, Object> resultMap : resultList) {
            RecommendItemVO recommendItemVO = this.convertToVO(resultMap);
            recommendItemVOList.add(recommendItemVO);
        }
        System.out.println(IronUtil.toJson(recommendItemVOList));
        return recommendItemVOList;
    }

    private RecommendItemVO convertToVO(Map<String, Object> resultMap) throws GlobalException {
        RecommendItemVO recommendItemVO = new RecommendItemVO();
        String type;
        if (resultMap.get("type") == null) {
            log.error("type不能为空");
            throw new GlobalException(ResponseStatus.SYSTEM_ERROR);
        }
        type = resultMap.get("type").toString();
        recommendItemVO.setType(resultMap.get("type").toString());
        if (type.equals(RecommendItemVO.TYPE.LOCAL.getName())) {
            if (resultMap.get("blog_id") == null) {
                log.error("blog_id不能为空");
                throw new GlobalException(ResponseStatus.SYSTEM_ERROR);
            }
            String blogUniqueId = resultMap.get("blog_id").toString();
            recommendItemVO.setBlogId(blogUniqueId);
            if (resultMap.get("author_id") == null) {
                log.error("author_id不能为空");
                throw new GlobalException(ResponseStatus.SYSTEM_ERROR);
            }
            String authorId = resultMap.get("author_id").toString();
            recommendItemVO.setAuthorId(authorId);
            recommendItemVO.setUrl(this.concatBlogUrl(authorId, blogUniqueId));
            recommendItemVO.setOriginSite("站内推荐");
        } else if (type.equals(RecommendItemVO.TYPE.EXTERNAL.getName())) {
            if (resultMap.get("origin_site") == null) {
                log.error("origin_site不能为空");
                throw new GlobalException(ResponseStatus.SYSTEM_ERROR);
            }
            recommendItemVO.setOriginSite(resultMap.get("origin_site").toString());
            if (resultMap.get("url") == null) {
                log.error("origin_site不能为空");
                throw new GlobalException(ResponseStatus.SYSTEM_ERROR);
            }
            recommendItemVO.setUrl(resultMap.get("url").toString());
        } else {
            log.error("type不合法");
            throw new GlobalException(ResponseStatus.SYSTEM_ERROR);
        }

        if (resultMap.get("author") != null) {
            recommendItemVO.setAuthor(resultMap.get("author").toString());
        }
        if (resultMap.get("content") != null) {
            recommendItemVO.setContent(resultMap.get("content").toString());
        }
        if (resultMap.get("title") != null) {
            recommendItemVO.setTitle(resultMap.get("title").toString());
        }
        if (resultMap.get("pub_date") != null) {
            recommendItemVO.setPubDate(resultMap.get("pub_date").toString());
        }
        if (resultMap.get("weight") != null) {
            recommendItemVO.setWeight((double) resultMap.get("weight"));
        }
        return recommendItemVO;
    }

    private String concatBlogUrl(String userUniqueId, String blogUniqueId) {
        String origin = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Origin");
//        String origin = "http://localhost:8080";
        StringBuilder sb = new StringBuilder();
        sb.append(IronConstant.SLASH)
                .append("user").append(IronConstant.SLASH)
                .append(userUniqueId).append(IronConstant.SLASH).append("blog")
                .append(IronConstant.SLASH)
                .append(blogUniqueId);
        return sb.toString();
    }
}
