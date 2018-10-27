package pe.com.iquitos.app.repository;

import pe.com.iquitos.app.domain.OrderProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the OrderProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    @Query(value = "select distinct order_product from OrderProduct order_product left join fetch order_product.products",
        countQuery = "select count(distinct order_product) from OrderProduct order_product")
    Page<OrderProduct> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct order_product from OrderProduct order_product left join fetch order_product.products")
    List<OrderProduct> findAllWithEagerRelationships();

    @Query("select order_product from OrderProduct order_product left join fetch order_product.products where order_product.id =:id")
    Optional<OrderProduct> findOneWithEagerRelationships(@Param("id") Long id);

}
