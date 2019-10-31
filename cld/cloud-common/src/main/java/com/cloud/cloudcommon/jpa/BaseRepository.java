package com.cloud.cloudcommon.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;
import java.util.List;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/4 11:08
 * @Modified By:
 */

//构造动态查询仓库接口
@NoRepositoryBean
public interface BaseRepository<T,ID extends Serializable> extends PagingAndSortingRepository<T,ID> {

    int count(Specification<T> specification);
    Page<T> findAll(Specification<T> specification, Pageable pageable);
    List<T> findAll(Specification<T> specification);

}
