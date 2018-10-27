package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.OrderProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OrderProduct and its DTO OrderProductDTO.
 */
@Mapper(componentModel = "spring", uses = {ProviderMapper.class, ProductMapper.class})
public interface OrderProductMapper extends EntityMapper<OrderProductDTO, OrderProduct> {

    @Mapping(source = "provider.id", target = "providerId")
    OrderProductDTO toDto(OrderProduct orderProduct);

    @Mapping(source = "providerId", target = "provider")
    OrderProduct toEntity(OrderProductDTO orderProductDTO);

    default OrderProduct fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setId(id);
        return orderProduct;
    }
}
