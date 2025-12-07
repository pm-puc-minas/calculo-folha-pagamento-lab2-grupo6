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
  daysWorked?: number;
  actualVTCost?: number;
  degreeUnhealthiness?: DegreeUnhealthiness;
  hasDanger?: boolean;
  isAdmin: boolean;
}

export interface PayrollResponse {
  id: number;
  employee: User;
  month: number;
  year: number;
  grossSalary: number;
  dangerAllowance: number;
  valueTransportDiscount: number;
  fgts: number;
  daysWorked: number;
  inssDiscount: number;
  irrfDiscount: number;
  netSalary: number;
  unhealthyAllowance: number;
}
