package com.modaexpress.modaexpress_backend.service;

import com.modaexpress.modaexpress_backend.dto.LoyaltyResult;
import com.modaexpress.modaexpress_backend.repository.JdbcLoyaltyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class LoyaltyService {

    private final JdbcLoyaltyRepository repo;

    public LoyaltyService(JdbcLoyaltyRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public LoyaltyResult applyPointsToOrder(Long pedidoId) {
        Map<String, Object> out = repo.assignPoints(pedidoId);
        // keys as declared in SP
        Integer puntos = null;
        Object pObj = out.get("p_puntos_asignados");
        if (pObj instanceof Number) puntos = ((Number)pObj).intValue();
        String msg = out.get("p_msg")!=null?out.get("p_msg").toString():null;
        return new LoyaltyResult(puntos!=null?puntos:0, msg!=null?msg:"No message");
    }
}
