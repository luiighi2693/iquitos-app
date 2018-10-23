import { element, by, ElementFinder } from 'protractor';

export class EmployeeComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-employee div table .btn-danger'));
    title = element.all(by.css('jhi-employee div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class EmployeeUpdatePage {
    pageTitle = element(by.id('jhi-employee-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    firstNameInput = element(by.id('field_firstName'));
    lastNameInput = element(by.id('field_lastName'));
    dniInput = element(by.id('field_dni'));
    addressInput = element(by.id('field_address'));
    emailInput = element(by.id('field_email'));
    birthdayInput = element(by.id('field_birthday'));
    sexSelect = element(by.id('field_sex'));
    cellphoneInput = element(by.id('field_cellphone'));
    imageInput = element(by.id('file_image'));
    employeeRoleSelect = element(by.id('field_employeeRole'));
    userSelect = element(by.id('field_user'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setFirstNameInput(firstName) {
        await this.firstNameInput.sendKeys(firstName);
    }

    async getFirstNameInput() {
        return this.firstNameInput.getAttribute('value');
    }

    async setLastNameInput(lastName) {
        await this.lastNameInput.sendKeys(lastName);
    }

    async getLastNameInput() {
        return this.lastNameInput.getAttribute('value');
    }

    async setDniInput(dni) {
        await this.dniInput.sendKeys(dni);
    }

    async getDniInput() {
        return this.dniInput.getAttribute('value');
    }

    async setAddressInput(address) {
        await this.addressInput.sendKeys(address);
    }

    async getAddressInput() {
        return this.addressInput.getAttribute('value');
    }

    async setEmailInput(email) {
        await this.emailInput.sendKeys(email);
    }

    async getEmailInput() {
        return this.emailInput.getAttribute('value');
    }

    async setBirthdayInput(birthday) {
        await this.birthdayInput.sendKeys(birthday);
    }

    async getBirthdayInput() {
        return this.birthdayInput.getAttribute('value');
    }

    async setSexSelect(sex) {
        await this.sexSelect.sendKeys(sex);
    }

    async getSexSelect() {
        return this.sexSelect.element(by.css('option:checked')).getText();
    }

    async sexSelectLastOption() {
        await this.sexSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setCellphoneInput(cellphone) {
        await this.cellphoneInput.sendKeys(cellphone);
    }

    async getCellphoneInput() {
        return this.cellphoneInput.getAttribute('value');
    }

    async setImageInput(image) {
        await this.imageInput.sendKeys(image);
    }

    async getImageInput() {
        return this.imageInput.getAttribute('value');
    }

    async setEmployeeRoleSelect(employeeRole) {
        await this.employeeRoleSelect.sendKeys(employeeRole);
    }

    async getEmployeeRoleSelect() {
        return this.employeeRoleSelect.element(by.css('option:checked')).getText();
    }

    async employeeRoleSelectLastOption() {
        await this.employeeRoleSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async userSelectLastOption() {
        await this.userSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async userSelectOption(option) {
        await this.userSelect.sendKeys(option);
    }

    getUserSelect(): ElementFinder {
        return this.userSelect;
    }

    async getUserSelectedOption() {
        return this.userSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class EmployeeDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-employee-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-employee'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
