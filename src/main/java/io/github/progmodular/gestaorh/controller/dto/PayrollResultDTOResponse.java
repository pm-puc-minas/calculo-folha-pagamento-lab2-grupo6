//package io.github.progmodular.gestaorh.controller.dto;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import io.github.progmodular.gestaorh.model.entities.PayrollResult; // Importe a entidade
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//@JsonInclude(JsonInclude.Include.NON_NULL)
//public record PayrollResultDTOResponse(
//        Long id,
//
//        // Objeto DTO aninhado para o funcionário
//        UserDTOResponse employee,
//
//        Integer month,
//        Integer year,
//        BigDecimal grossSalary,
//        BigDecimal dangerAllowance,
//        BigDecimal valueTransportDiscount,
//        BigDecimal fgts,
//        BigDecimal foodValueAllowance,
//        BigDecimal hoursSalary,
//        BigDecimal inssDiscount,
//        BigDecimal irrfDiscount,
//        BigDecimal netSalary,
//        BigDecimal unhealthyAllowance,
//        LocalDateTime calculationDate
//) {
//    /**
//     * ✅ Construtor de Mapeamento (Factory Constructor):
//     * Mapeia a entidade PayrollResult para o DTO de resposta.
//     */
//    public PayrollResultDTOResponse(PayrollResult entity) {
//        this(
//                entity.getId(),
//
//                entity.getEmployee(),
//
//                entity.getMonth(),
//                entity.getYear(),
//                entity.getGrossSalary(),
//                entity.getDangerAllowance(),
//                entity.getValueTransportDiscount(),
//                entity.getFgts(),
//                entity.getFoodValueAllowance(),
//                entity.getHoursSalary(),
//                entity.getInssDiscount(),
//                entity.getIrrfDiscount(),
//                entity.getNetSalary(),
//                entity.getUnhealthyAllowance(),
//                entity.getCalculationDate()
//        );
//    }
//}