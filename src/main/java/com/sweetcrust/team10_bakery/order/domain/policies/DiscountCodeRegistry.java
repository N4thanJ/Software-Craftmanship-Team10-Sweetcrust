package com.sweetcrust.team10_bakery.order.domain.policies;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class DiscountCodeRegistry {

  private final Map<String, DiscountCodePolicy> byCode;
  private final DiscountCodePolicy fallback;

  public DiscountCodeRegistry(List<DiscountCodePolicy> policies) {
    this.byCode =
        policies.stream()
            .collect(
                Collectors.toMap(p -> p.code().toUpperCase(), Function.identity(), (a, b) -> a));
    this.fallback =
        byCode.values().stream()
            .filter(p -> "NO_DISCOUNT".equalsIgnoreCase(p.code()))
            .findFirst()
            .orElseGet(NoOpDiscountCodePolicy::new);
  }

  public DiscountCodePolicy getByCode(String code) {
    if (code == null) return fallback;
    DiscountCodePolicy policy = byCode.getOrDefault(code.toUpperCase(), fallback);
    if (policy.isExpired()) return fallback;
    return policy;
  }
}
