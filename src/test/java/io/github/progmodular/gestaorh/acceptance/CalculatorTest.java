package io.github.progmodular.gestaorh.acceptance;

import io.github.progmodular.gestaorh.infra.config.calculator.*;
import io.github.progmodular.gestaorh.model.entities.Employee;
import io.github.progmodular.gestaorh.model.Enum.DegreeUnhealthiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CalculatorAllTests {

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();

        employee.setGrossSalary(new BigDecimal("3000.00"));
        employee.setActualVTCost(new BigDecimal("100.00"));
        employee.setDependents(0);

        employee.setDegreeUnhealthiness(DegreeUnhealthiness.MEDIUM);
    }

    private void assertBigDecimalEquals(BigDecimal expected, BigDecimal actual) {
        assertEquals(0, expected.compareTo(actual),
                "Expected: " + expected + " but was: " + actual);
    }

    @Test
    void testCalculatorDanger_WithValidSalary_ShouldCalculateCorrectly() {
        CalculatorDanger calculator = new CalculatorDanger(employee);
        BigDecimal expected = employee.getGrossSalary().multiply(new BigDecimal("0.30"));

        BigDecimal result = calculator.calculator();

        assertBigDecimalEquals(expected, result);
    }

    @Test
    void testCalculatorDanger_WithNullEmployee_ShouldThrowException() {
        CalculatorDanger nullCalculator = new CalculatorDanger(null);

        assertThrows(IllegalStateException.class, () -> nullCalculator.calculator());
    }

    @Test
    void testCalculatorDanger_WithNullGrossSalary_ShouldThrowException() {
        employee.setGrossSalary(null);
        CalculatorDanger calculator = new CalculatorDanger(employee);

        assertThrows(IllegalStateException.class, () -> calculator.calculator());
    }

    @Test
    void testCalculatorDanger_GetCalculationType_ShouldReturnCorrectType() {
        CalculatorDanger calculator = new CalculatorDanger(employee);

        String result = calculator.getCalculationType();

        assertEquals("DANGER", result);
    }

    @Test
    void testCalculatorDiscountValueTransport_WhenActualCostIsLessThanMaxDiscount_ShouldReturnActualCost() {
        CalculatorDiscountValueTransport calculator = new CalculatorDiscountValueTransport(employee);
        BigDecimal actualVTCost = new BigDecimal("100.00");
        employee.setActualVTCost(actualVTCost);

        BigDecimal result = calculator.calculator();

        assertBigDecimalEquals(actualVTCost, result);
    }

    @Test
    void testCalculatorDiscountValueTransport_WhenActualCostIsGreaterThanMaxDiscount_ShouldReturnMaxDiscount() {
        CalculatorDiscountValueTransport calculator = new CalculatorDiscountValueTransport(employee);
        BigDecimal actualVTCost = new BigDecimal("200.00");
        employee.setActualVTCost(actualVTCost);
        BigDecimal expected = employee.getGrossSalary().multiply(new BigDecimal("0.06"));

        BigDecimal result = calculator.calculator();

        assertBigDecimalEquals(expected, result);
    }

    @Test
    void testCalculatorDiscountValueTransport_ResultShouldBeRoundedToTwoDecimals() {
        CalculatorDiscountValueTransport calculator = new CalculatorDiscountValueTransport(employee);
        BigDecimal actualVTCost = new BigDecimal("90.123");
        employee.setActualVTCost(actualVTCost);

        BigDecimal result = calculator.calculator();

        assertEquals(2, result.scale());
        assertBigDecimalEquals(new BigDecimal("90.12"), result);
    }

    @Test
    void testCalculatorDiscountValueTransport_GetCalculationType_ShouldReturnCorrectType() {
        CalculatorDiscountValueTransport calculator = new CalculatorDiscountValueTransport(employee);

        String result = calculator.getCalculationType();

        assertEquals("VALUE_TRANSPORT", result);
    }

    @Test
    void testCalculatorFgts_WithValidSalary_ShouldCalculateCorrectly() {
        CalculatorFgts calculator = new CalculatorFgts(employee);
        BigDecimal expected = employee.getGrossSalary().multiply(new BigDecimal("0.08"));

        BigDecimal result = calculator.calculator();

        assertBigDecimalEquals(expected, result);
    }

    @Test
    void testCalculatorFgts_WithZeroSalary_ShouldReturnZero() {
        employee.setGrossSalary(BigDecimal.ZERO);
        CalculatorFgts calculator = new CalculatorFgts(employee);

        BigDecimal result = calculator.calculator();

        assertBigDecimalEquals(BigDecimal.ZERO, result);
    }

    @Test
    void testCalculatorFgts_GetCalculationType_ShouldReturnCorrectType() {
        CalculatorFgts calculator = new CalculatorFgts(employee);

        String result = calculator.getCalculationType();

        assertEquals("FGTS", result);
    }



    @Test
    void testCalculatorInss_FirstBracket_ShouldCalculateCorrectly() {
        employee.setGrossSalary(new BigDecimal("1400.00"));
        CalculatorInss calculator = new CalculatorInss(employee);
        BigDecimal expected = employee.getGrossSalary().multiply(new BigDecimal("0.075"));

        BigDecimal result = calculator.calculator();

        assertBigDecimalEquals(expected.setScale(2, java.math.RoundingMode.HALF_UP), result);
    }

    @Test
    void testCalculatorInss_SecondBracket_ShouldCalculateCorrectly() {
        employee.setGrossSalary(new BigDecimal("2000.00"));
        CalculatorInss calculator = new CalculatorInss(employee);
        BigDecimal expected = new BigDecimal("1412.00").multiply(new BigDecimal("0.075"))
                .add(employee.getGrossSalary().subtract(new BigDecimal("1412.00")).multiply(new BigDecimal("0.09")));

        BigDecimal result = calculator.calculator();

        assertBigDecimalEquals(expected.setScale(2, java.math.RoundingMode.HALF_UP), result);
    }

    @Test
    void testCalculatorInss_ThirdBracket_ShouldCalculateCorrectly() {
        employee.setGrossSalary(new BigDecimal("3500.00"));
        CalculatorInss calculator = new CalculatorInss(employee);

        BigDecimal result = calculator.calculator();

        assertTrue(result.compareTo(BigDecimal.ZERO) > 0);
        assertEquals(2, result.scale());
    }

    @Test
    void testCalculatorInss_FourthBracket_ShouldCalculateCorrectly() {
        employee.setGrossSalary(new BigDecimal("5000.00"));
        CalculatorInss calculator = new CalculatorInss(employee);

        BigDecimal result = calculator.calculator();

        assertTrue(result.compareTo(BigDecimal.ZERO) > 0);
        assertEquals(2, result.scale());
    }

    @Test
    void testCalculatorInss_AboveMaximum_ShouldCalculateCorrectly() {
        employee.setGrossSalary(new BigDecimal("8000.00"));
        CalculatorInss calculator = new CalculatorInss(employee);

        BigDecimal result = calculator.calculator();

        assertTrue(result.compareTo(BigDecimal.ZERO) > 0);
        assertEquals(2, result.scale());
    }

    @Test
    void testCalculatorInss_GetCalculationType_ShouldReturnCorrectType() {
        CalculatorInss calculator = new CalculatorInss(employee);

        String result = calculator.getCalculationType();

        assertEquals("INSS", result);
    }

    @Test
    void testCalculatorIrrf_FirstBracket_ShouldReturnZero() {
        employee.setGrossSalary(new BigDecimal("2000.00"));
        employee.setDependents(0);
        CalculatorIrrf calculator = new CalculatorIrrf(employee);

        BigDecimal result = calculator.calculator();

        assertBigDecimalEquals(BigDecimal.ZERO, result);
    }

    @Test
    void testCalculatorIrrf_SecondBracket_ShouldCalculateCorrectly() {
        employee.setGrossSalary(new BigDecimal("3000.00"));
        employee.setDependents(0);
        CalculatorIrrf calculator = new CalculatorIrrf(employee);

        BigDecimal result = calculator.calculator();

        assertTrue(result.compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testCalculatorIrrf_WithDependents_ShouldReduceBaseCalculation() {
        employee.setGrossSalary(new BigDecimal("3500.00"));
        employee.setDependents(2);
        CalculatorIrrf calculator = new CalculatorIrrf(employee);

        BigDecimal result = calculator.calculator();

        assertTrue(result.compareTo(BigDecimal.ZERO) >= 0);
    }

    @Test
    void testCalculatorIrrf_NegativeResult_ShouldReturnZero() {
        employee.setGrossSalary(new BigDecimal("2500.00"));
        employee.setDependents(5);
        CalculatorIrrf calculator = new CalculatorIrrf(employee);

        BigDecimal result = calculator.calculator();

        assertBigDecimalEquals(BigDecimal.ZERO, result);
    }

    @Test
    void testCalculatorIrrf_GetCalculationType_ShouldReturnCorrectType() {
        CalculatorIrrf calculator = new CalculatorIrrf(employee);

        String result = calculator.getCalculationType();

        assertEquals("IRRF", result);
    }

    @Test
    void testCalculatorNetSalary_WithAllDiscounts_ShouldCalculateCorrectly() {
        CalculatorNetSalary calculator = new CalculatorNetSalary(employee);

        BigDecimal result = calculator.calculator();

        assertTrue(result.compareTo(BigDecimal.ZERO) > 0);
        assertTrue(result.compareTo(employee.getGrossSalary()) < 0);
    }

    @Test
    void testCalculatorNetSalary_WithHighSalary_ShouldCalculateCorrectly() {
        employee.setGrossSalary(new BigDecimal("5000.00"));
        employee.setActualVTCost(new BigDecimal("150.00"));
        employee.setDependents(1);
        CalculatorNetSalary calculator = new CalculatorNetSalary(employee);

        BigDecimal result = calculator.calculator();

        assertTrue(result.compareTo(BigDecimal.ZERO) > 0);
        assertTrue(result.compareTo(employee.getGrossSalary()) < 0);
    }

    @Test
    void testCalculatorNetSalary_GetCalculationType_ShouldReturnCorrectType() {
        CalculatorNetSalary calculator = new CalculatorNetSalary(employee);

        String result = calculator.getCalculationType();

        assertEquals("NET_SALARY", result);
    }

    @Test
    void testCalculatorUnhealthiness_WithValidDegree_ShouldCalculateCorrectly() {
        CalculatorUnhealthiness calculator = new CalculatorUnhealthiness(employee);

        BigDecimal percentage = employee.getDegreeUnhealthiness().getPercentage();
        BigDecimal expected = new BigDecimal("1518.00").multiply(percentage);

        BigDecimal result = calculator.calculator();

        assertBigDecimalEquals(expected, result);
    }

    @Test
    void testCalculatorUnhealthiness_WithDifferentDegree_ShouldCalculateCorrectly() {
        employee.setDegreeUnhealthiness(DegreeUnhealthiness.MAXIMUM);
        CalculatorUnhealthiness calculator = new CalculatorUnhealthiness(employee);

        BigDecimal percentage = employee.getDegreeUnhealthiness().getPercentage();
        BigDecimal expected = new BigDecimal("1518.00").multiply(percentage);

        BigDecimal result = calculator.calculator();

        assertBigDecimalEquals(expected, result);
    }

    @Test
    void testCalculatorUnhealthiness_GetCalculationType_ShouldReturnCorrectType() {
        CalculatorUnhealthiness calculator = new CalculatorUnhealthiness(employee);

        String result = calculator.getCalculationType();

        assertEquals("UNHEALTHY", result);
    }

    @Test
    void testCalculatorAbstract_Fields_ShouldHaveCorrectValues() {
        CalculatorFgts calculator = new CalculatorFgts(employee);

        assertNotNull(calculator.getMinimunSalary());
        assertNotNull(calculator.getFgtsPercentage());
        assertNotNull(calculator.getDangerPercentage());

        assertBigDecimalEquals(new BigDecimal("1518.00"), calculator.getMinimunSalary());
        assertBigDecimalEquals(new BigDecimal("0.08"), calculator.getFgtsPercentage());
        assertBigDecimalEquals(new BigDecimal("0.30"), calculator.getDangerPercentage());
    }

    @Test
    void testICalculatorInterface_Contract_ShouldBeImplementedCorrectly() {
        ICalculatorInterface calculator = new CalculatorFgts(employee);

        assertNotNull(calculator.calculator());
        assertNotNull(calculator.getCalculationType());
        assertFalse(calculator.getCalculationType().isEmpty());
    }

    @Test
    void testIntegration_NetSalaryUsesOtherCalculatorsCorrectly() {
        CalculatorNetSalary netSalaryCalculator = new CalculatorNetSalary(employee);
        CalculatorInss inssCalculator = new CalculatorInss(employee);
        CalculatorIrrf irrfCalculator = new CalculatorIrrf(employee);
        CalculatorDiscountValueTransport vtCalculator = new CalculatorDiscountValueTransport(employee);

        BigDecimal netSalary = netSalaryCalculator.calculator();
        BigDecimal inss = inssCalculator.calculator();
        BigDecimal irrf = irrfCalculator.calculator();
        BigDecimal vt = vtCalculator.calculator();
        BigDecimal expectedNetSalary = employee.getGrossSalary().subtract(inss).subtract(irrf).subtract(vt);

        assertBigDecimalEquals(expectedNetSalary, netSalary);
    }

    @Test
    void testIntegration_AllCalculatorsReturnValidResults() {
        ICalculatorInterface[] calculators = {
                new CalculatorDanger(employee),
                new CalculatorDiscountValueTransport(employee),
                new CalculatorFgts(employee),
                new CalculatorInss(employee),
                new CalculatorIrrf(employee),
                new CalculatorNetSalary(employee),
                new CalculatorUnhealthiness(employee)
        };

        for (ICalculatorInterface calculator : calculators) {
            BigDecimal result = calculator.calculator();
            String calculationType = calculator.getCalculationType();

            assertNotNull(result, "Result should not be null for: " + calculationType);
            assertNotNull(calculationType, "Calculation type should not be null for: " + calculator.getClass().getSimpleName());
            assertFalse(calculationType.isEmpty(), "Calculation type should not be empty for: " + calculator.getClass().getSimpleName());
        }
    }
}