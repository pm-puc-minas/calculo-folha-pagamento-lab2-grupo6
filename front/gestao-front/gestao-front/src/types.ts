// src/types.ts

export enum UserType {
  EMPLOYEE = "EMPLOYEE",
  PAYROLL_ADMIN = "PAYROLL_ADMIN",
}

export enum DegreeUnhealthiness {
  MINIMUM = "MINIMUM",
  MEDIUM = "MEDIUM",
  MAXIMUM = "MAXIMUM",
}

// Baseado no UserDTOResponse
export interface User {
  id: number;
  name: string;
  email: string;
  userType: UserType;
  grossSalary?: number;
  cpf?: string;
  position?: string;
  dependents?: number;
  hoursWorkedMonth?: number;
  daysWorked?: number;
  actualVTCost?: number;
  degreeUnhealthiness?: DegreeUnhealthiness;
  hasDanger?: boolean;
  isAdmin: boolean;
}

// Baseado no PayrollDTOResponse
export interface PayrollResponse {
  id: number;
  employee: User; // O objeto UserDTOResponse aninhado
  month: number;
  year: number;
  grossSalary: number;
  dangerAllowance: number;
  valueTransportDiscount: number;
  fgts: number;
  hoursSalary: number;
  inssDiscount: number;
  irrfDiscount: number;
  netSalary: number;
  unhealthyAllowance: number;
}
