package net.okjsp

import grails.plugin.mail.MailService

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AdminCompanyController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    MailService mailService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.order = params.order ?: 'desc'
        params.sort = params.sort ?: 'id'


        def list, count

        if(params.where) {

            def companies = Company.where {
                name =~ "%${params.where}%" ||
                        registerNumber =~ "%${params.where}%"
            }

            list = companies.list(params)
            count = companies.count()

        } else {
            list = Company.list(params)
            count = Company.count()
        }

        respond list, model:[companyCount: count]
    }

    def show(Company company) {
        def companyInfo = CompanyInfo.findByCompany(company)

        def user = User.findByPerson(company.manager)

        respond company, model: [companyInfo: companyInfo, user: user]
    }

    def create() {
        respond new Company(params)
    }

    @Transactional
    def save(Company company) {
        if (company == null) {
            notFound()
            return
        }

        if (company.hasErrors()) {
            respond company.errors, view:'create'
            return
        }

        company.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'company.label', default: 'Company'), company.id])
                redirect company
            }
            '*' { respond company, [status: CREATED] }
        }
    }

    def edit(Company company) {
        respond company
    }

    @Transactional
    def update(Company company) {
        if (company == null) {
            notFound()
            return
        }

        if (company.hasErrors()) {
            respond company.errors, view:'edit'
            return
        }

        company.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Company.label', default: 'Company'), company.id])
                redirect uri: '/_admin/company/show/'+company.id
            }
            '*'{ respond company, [status: OK] }
        }
    }

    @Transactional
    def delete(Company companyInstance) {

        if (companyInstance == null) {
            notFound()
            return
        }

        Person.where {
            company == companyInstance
        }.updateAll(company: null)

        CompanyInfo.where {
            company == companyInstance
        }.deleteAll()

        companyInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Company.label', default: 'Company'), companyInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    @Transactional
    def enable(Company company) {
        if (company == null) {
            notFound()
            return
        }

        def companyInfo = CompanyInfo.findByCompany(company)

        company.enabled = true
        company.save flush: true


        mailService.sendMail {
            async true
            to companyInfo?.email, companyInfo.managerEmail
            from "OKKY JOBS <jobs@okky.kr>"
            subject "["+message(code:'email.company.enabled.subject')+"] ${company.name}의 회사정보 인증이 완료되었습니다."
            body(view:'/email/company_enabled', model: [company: company, companyInfo: companyInfo, grailsApplication: grailsApplication] )
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'Company.label', default: 'Company'), company.id])
        redirect uri: '/_admin/company/show/'+company.id
    }

    @Transactional
    def disable(Company company) {
        if (company == null) {
            notFound()
            return
        }

        company.enabled = false
        company.save flush: true

        flash.message = message(code: 'default.updated.message', args: [message(code: 'Company.label', default: 'Company'), company.id])
        redirect uri: '/_admin/company/show/'+company.id

    }

    protected void notFound() {
        request.withFormat {
            form  {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'company.label', default: 'Company'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}