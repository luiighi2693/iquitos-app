import { element, by, ElementFinder } from 'protractor';

export class ProviderComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-provider div table .btn-danger'));
    title = element.all(by.css('jhi-provider div h2#page-heading span')).first();

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

export class ProviderUpdatePage {
    pageTitle = element(by.id('jhi-provider-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    codeInput = element(by.id('field_code'));
    socialReasonInput = element(by.id('field_socialReason'));
    addressInput = element(by.id('field_address'));
    emailInput = element(by.id('field_email'));
    cellphoneInput = element(by.id('field_cellphone'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setCodeInput(code) {
        await this.codeInput.sendKeys(code);
    }

    async getCodeInput() {
        return this.codeInput.getAttribute('value');
    }

    async setSocialReasonInput(socialReason) {
        await this.socialReasonInput.sendKeys(socialReason);
    }

    async getSocialReasonInput() {
        return this.socialReasonInput.getAttribute('value');
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

export class ProviderDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-provider-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-provider'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
