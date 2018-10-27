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
            cm.createCache(pe.com.iquitos.app.domain.Amortization.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Sell.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Sell.class.getName() + ".amortizations", jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Sell.class.getName() + ".products", jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Credit.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Client.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Employee.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Product.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Product.class.getName() + ".variants", jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Variant.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Variant.class.getName() + ".products", jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Category.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Provider.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Provider.class.getName() + ".providerAccounts", jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.ProviderAccount.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.ProviderPayment.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Purchase.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Purchase.class.getName() + ".products", jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Box.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.Operation.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.DocumentType.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.PaymentType.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.DocumentTypeSell.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.ProductsDeliveredStatus.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.UnitMeasurement.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.DocumentTypePurchase.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.PurchaseStatus.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.ConceptOperationInput.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.ConceptOperationOutput.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.OrderProduct.class.getName(), jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.OrderProduct.class.getName() + ".products", jcacheConfiguration);
            cm.createCache(pe.com.iquitos.app.domain.UserLogin.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
