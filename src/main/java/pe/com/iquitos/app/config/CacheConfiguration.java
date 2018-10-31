package pe.com.iquitos.app.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(pe.com.iquitos.app.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Amortizacion.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Venta.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Venta.class.getName() + ".amortizacions", jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Venta.class.getName() + ".productos", jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Credito.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Cliente.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Empleado.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Producto.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Producto.class.getName() + ".variantes", jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Variante.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Variante.class.getName() + ".productos", jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Categoria.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Proveedor.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Proveedor.class.getName() + ".cuentaProveedors", jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.CuentaProveedor.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.PagoDeProveedor.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Compra.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Compra.class.getName() + ".productos", jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Caja.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Operacion.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.UsuarioExterno.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Pedido.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Pedido.class.getName() + ".productos", jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.TipoDeDocumento.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.TipoDePago.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.TipoDeDocumentoDeVenta.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.EstatusDeProductoEntregado.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.UnidadDeMedida.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.TipoDeDocumentoDeCompra.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.EstatusDeCompra.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.TipoDeOperacionDeIngreso.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.TipoDeOperacionDeGasto.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
