import { element, by, ElementFinder } from 'protractor';

export class ClientComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-client div table .btn-danger'));
    title = element.all(by.css('jhi-client div h2#page-heading span')).first();

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

export class ClientUpdatePage {
    pageTitle = element(by.id('jhi-client-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    codeInput = element(by.id('field_code'));
    addressInput = element(by.id('field_address'));
    emailInput = element(by.id('field_email'));
    cellphoneInput = element(by.id('field_cellphone'));
    birthdayInput = element(by.id('field_birthday'));
    sexSelect = element(by.id('field_sex'));
    civilStatusSelect = element(by.id('field_civilStatus'));
    imageInput = element(by.id('file_image'));
    clientTypeSelect = element(by.id('field_clientType'));
    userSelect = element(by.id('field_user'));
    documentTypeSelect = element(by.id('field_documentType'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    async setCodeInput(code) {
        await this.codeInput.sendKeys(code);
    }

    async getCodeInput() {
        return this.codeInput.getAttribute('value');
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

    async setCellphoneInput(cellphone) {
        await this.cellphoneInput.sendKeys(cellphone);
    }

    async getCellphoneInput() {
        return this.cellphoneInput.getAttribute('value');
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

    async setCivilStatusSelect(civilStatus) {
        await this.civilStatusSelect.sendKeys(civilStatus);
    }

    async getCivilStatusSelect() {
        return this.civilStatusSelect.element(by.css('option:checked')).getText();
    }

    async civilStatusSelectLastOption() {
        await this.civilStatusSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setImageInput(image) {
        await this.imageInput.sendKeys(image);
    }

    async getImageInput() {
        return this.imageInput.getAttribute('value');
    }

    async setClientTypeSelect(clientType) {
        await this.clientTypeSelect.sendKeys(clientType);
    }

    async getClientTypeSelect() {
        return this.clientTypeSelect.element(by.css('option:checked')).getText();
    }

    async clientTypeSelectLastOption() {
        await this.clientTypeSelect
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

    async documentTypeSelectLastOption() {
        await this.documentTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async documentTypeSelectOption(option) {
        await this.documentTypeSelect.sendKeys(option);
    }

    getDocumentTypeSelect(): ElementFinder {
        return this.documentTypeSelect;
    }

    async getDocumentTypeSelectedOption() {
        return this.documentTypeSelect.element(by.css('option:checked')).getText();
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

export class ClientDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-client-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-client'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
