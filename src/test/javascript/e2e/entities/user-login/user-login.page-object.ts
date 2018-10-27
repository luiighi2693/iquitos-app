import { element, by, ElementFinder } from 'protractor';

export class UserLoginComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-user-login div table .btn-danger'));
    title = element.all(by.css('jhi-user-login div h2#page-heading span')).first();

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

export class UserLoginUpdatePage {
    pageTitle = element(by.id('jhi-user-login-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    dniInput = element(by.id('field_dni'));
    pinInput = element(by.id('field_pin'));
    emailInput = element(by.id('field_email'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setDniInput(dni) {
        await this.dniInput.sendKeys(dni);
    }

    async getDniInput() {
        return this.dniInput.getAttribute('value');
    }

    async setPinInput(pin) {
        await this.pinInput.sendKeys(pin);
    }

    async getPinInput() {
        return this.pinInput.getAttribute('value');
    }

    async setEmailInput(email) {
        await this.emailInput.sendKeys(email);
    }

    async getEmailInput() {
        return this.emailInput.getAttribute('value');
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

export class UserLoginDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-userLogin-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-userLogin'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
