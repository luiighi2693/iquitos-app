/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EmployeeComponentsPage, EmployeeDeleteDialog, EmployeeUpdatePage } from './employee.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Employee e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let employeeUpdatePage: EmployeeUpdatePage;
    let employeeComponentsPage: EmployeeComponentsPage;
    let employeeDeleteDialog: EmployeeDeleteDialog;
    const fileNameToUpload = 'logo-jhipster.png';
    const fileToUpload = '../../../../../main/webapp/content/images/' + fileNameToUpload;
    const absolutePath = path.resolve(__dirname, fileToUpload);

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Employees', async () => {
        await navBarPage.goToEntity('employee');
        employeeComponentsPage = new EmployeeComponentsPage();
        expect(await employeeComponentsPage.getTitle()).to.eq('Employees');
    });

    it('should load create Employee page', async () => {
        await employeeComponentsPage.clickOnCreateButton();
        employeeUpdatePage = new EmployeeUpdatePage();
        expect(await employeeUpdatePage.getPageTitle()).to.eq('Create or edit a Employee');
        await employeeUpdatePage.cancel();
    });

    it('should create and save Employees', async () => {
        const nbButtonsBeforeCreate = await employeeComponentsPage.countDeleteButtons();

        await employeeComponentsPage.clickOnCreateButton();
        await promise.all([
            employeeUpdatePage.setFirstNameInput('firstName'),
            employeeUpdatePage.setLastNameInput('lastName'),
            employeeUpdatePage.setDniInput('5'),
            employeeUpdatePage.setAddressInput('address'),
            employeeUpdatePage.setEmailInput('email'),
            employeeUpdatePage.setBirthdayInput('2000-12-31'),
            employeeUpdatePage.sexSelectLastOption(),
            employeeUpdatePage.setCellphoneInput('5'),
            employeeUpdatePage.setImageInput(absolutePath),
            employeeUpdatePage.employeeRoleSelectLastOption(),
            employeeUpdatePage.userSelectLastOption()
        ]);
        expect(await employeeUpdatePage.getFirstNameInput()).to.eq('firstName');
        expect(await employeeUpdatePage.getLastNameInput()).to.eq('lastName');
        expect(await employeeUpdatePage.getDniInput()).to.eq('5');
        expect(await employeeUpdatePage.getAddressInput()).to.eq('address');
        expect(await employeeUpdatePage.getEmailInput()).to.eq('email');
        expect(await employeeUpdatePage.getBirthdayInput()).to.eq('2000-12-31');
        expect(await employeeUpdatePage.getCellphoneInput()).to.eq('5');
        expect(await employeeUpdatePage.getImageInput()).to.endsWith(fileNameToUpload);
        await employeeUpdatePage.save();
        expect(await employeeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await employeeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Employee', async () => {
        const nbButtonsBeforeDelete = await employeeComponentsPage.countDeleteButtons();
        await employeeComponentsPage.clickOnLastDeleteButton();

        employeeDeleteDialog = new EmployeeDeleteDialog();
        expect(await employeeDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Employee?');
        await employeeDeleteDialog.clickOnConfirmButton();

        expect(await employeeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
