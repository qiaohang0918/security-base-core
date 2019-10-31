package com.cloud.cloudcommon.jpa;

import com.cloud.cloudcommon.utils.CommonCheck;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/4 10:56
 * @Modified By:
 */

//定义一个查询条件容器
public class SpecificationBuffer<T> implements Specification<T> {

    private List<Criterion> criterions = new ArrayList<Criterion>();

    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
                                 CriteriaBuilder builder) {
        if (!criterions.isEmpty()) {
            List<Predicate> predicates = new ArrayList<Predicate>();
            for(Criterion c : criterions){
                predicates.add(c.toPredicate(root, query,builder));
            }
            // 将所有条件用 and 联合起来
            if (predicates.size() > 0) {
                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }
        return builder.conjunction();
    }
    /**
     * 增加简单条件表达式
     * @Methods Name add
     * @param
     */
    public SpecificationBuffer<T> add(Criterion criterion){

        if(criterion!=null){
            criterions.add(criterion);
        }

        return this;
    }

    /**
     * 自动构建条件对象
     * @param buffer
     * @param parm
     * @return
     */
    public static  SpecificationBuffer<?> autoBuilder(SpecificationBuffer buffer, Map<String,Object> parm,String... rules){
        if(CommonCheck.nullStrings(rules)){
            System.out.println("-----warning : current Query has not  Rules[] , constructSpecification will been Skiped!");
            return null;
        }
        if(CommonCheck.nullMap(parm)){
            System.out.println("-----warning : current Query has not  parm（Map<String,Object>）,constructSpecification will been Skiped!");
            return null;
        }
        for (String rule : rules) {
            constructKeyValue(buffer,parm,rule);
        }
        return buffer;
    }

    private static void constructKeyValue(SpecificationBuffer buffer, Map<String, Object> parm, String rule) {
        String[] split = rule.split("@");
        if(split.length<2)
            return;
        //受限key 和受限pattern
        String limitedKey =  split[0];
        String limitedPattren =  split[1];
        //获取key-value，构建条件
        Object value = parm.get(limitedKey);
        if(limitedPattren.equalsIgnoreCase("isNull")){
            buffer.add(SpecificationBuilder.isNull(limitedKey));
        }else if(limitedPattren.equalsIgnoreCase("isNotNull")){
            buffer.add(SpecificationBuilder.isNotNull(limitedKey));
        }else if(limitedPattren.equalsIgnoreCase("eq")){
            if(value==null)
                return;
            else
                buffer.add(SpecificationBuilder.eq(limitedKey,String.valueOf(value)));
        }else if(limitedPattren.equalsIgnoreCase("neq")){
            if(value==null)
                return;
            else
                buffer.add(SpecificationBuilder.neq(limitedKey,String.valueOf(value)));
        }else if(limitedPattren.equalsIgnoreCase("gt")){
            if(value==null)
                return;
            else
                buffer.add(SpecificationBuilder.gt(limitedKey,String.valueOf(value)));
        }else if(limitedPattren.equalsIgnoreCase("gte")){
            if(value==null)
                return;
            else
                buffer.add(SpecificationBuilder.gte(limitedKey,String.valueOf(value)));
        }else if(limitedPattren.equalsIgnoreCase("lt")){
            if(value==null)
                return;
            else
                buffer.add(SpecificationBuilder.lt(limitedKey,String.valueOf(value)));
        }else if(limitedPattren.equalsIgnoreCase("lte")){
            if(value==null)
                return;
            else
                buffer.add(SpecificationBuilder.lte(limitedKey,String.valueOf(value)));
        }else if(limitedPattren.equalsIgnoreCase("in")){
            if(value==null)
                return;
            else
                buffer.add(SpecificationBuilder.in(limitedKey,(List)value,true));
        }else if(limitedPattren.equalsIgnoreCase("nin")){
            if(value==null)
                return;
            else
                buffer.add(SpecificationBuilder.notin(limitedKey,(List)value,true));
        }else if(limitedPattren.equalsIgnoreCase("like")){
            if(value==null)
                return;
            else
                buffer.add(SpecificationBuilder.like(limitedKey,String.valueOf(value)));
        }else if(limitedPattren.equalsIgnoreCase("between")){
            if(value==null || !(value instanceof Object[]))
                return;
            else {
                Object[] objects = (Object[]) value;
                Object object0 = objects[0];    //start    object0
                Object object1 = objects[1];    //end    object1
                if(object0!=null)
                    buffer.add(SpecificationBuilder.gte(limitedKey,object0));
                if(object1!=null)
                    buffer.add(SpecificationBuilder.lte(limitedKey,object1));
            }
        }else {

        }
    }


}
