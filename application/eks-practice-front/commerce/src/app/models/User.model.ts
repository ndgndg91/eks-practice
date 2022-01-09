export class User {
  constructor(
    public id: number, public email: string, public fullName: string,
    public phone: string, public lastLoginAt: string, public updatedAt: string, public createdAt: string,
    public jwt: string, public roles: string[]
  ){}
}
