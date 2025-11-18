package com.sweetcrust.team10_bakery.order.domain.policies;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DiscountCodeRegistery {

   private final Map<String, DiscountCodePolicy> byCode;
   private final DiscountCodePolicy fallback;

   public DiscountCodeRegistery(List<DiscountCodePolicy> policies) {
       this.byCode = policies.stream()
               .collect(Collectors.toMap(p -> p.code().toUpperCase(), Function.identity(), (a, b) -> a));
       this.fallback = byCode.values().stream()
               .filter(p -> "NO_DISCOUNT".equalsIgnoreCase(p.code()))
               .findFirst()
               .orElseGet(() -> new NoOpDiscountCodePolicy());
   }

   public DiscountCodePolicy getByCode(String code) {
       if (code == null) return fallback;
       DiscountCodePolicy policy = byCode.getOrDefault(code.toUpperCase(), fallback);
       if (policy.isExpired()) return fallback;
       return policy;
   }
}
