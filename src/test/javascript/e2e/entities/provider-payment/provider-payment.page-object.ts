import { element, by, ElementFinder } from 'protractor';

export class ProviderPaymentComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-provider-payment div table .btn-danger'));
    title = element.all(by.css('jhi-provider-payment div h2#page-heading span')).first();

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

export class ProviderPaymentUpdatePage {
    pageTitle = element(by.id('jhi-provider-payment-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    amountToPayInput = element(by.id('field_amountToPay'));
    amountPayedInput = element(by.id('field_amountPayed'));
    emissionDateInput = element(by.id('field_emissionDate'));
    documentCodeInput = element(by.id('field_documentCode'));
    glosaInput = element(by.id('field_glosa'));
    imageInput = element(by.id('file_image'));
    documentTypePurchaseSelect = element(by.id('field_documentTypePurchase'));
    paymentTypeSelect = element(by.id('field_paymentType'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setAmountToPayInput(amountToPay) {
        await this.amountToPayInput.sendKeys(amountToPay);
    }

    async getAmountToPayInput() {
        return this.amountToPayInput.getAttribute('value');
    }

    async setAmountPayedInput(amountPayed) {
        await this.amountPayedInput.sendKeys(amountPayed);
    }

    async getAmountPayedInput() {
        return this.amountPayedInput.getAttribute('value');
    }

    async setEmissionDateInput(emissionDate) {
        await this.emissionDateInput.sendKeys(emissionDate);
    }

    async getEmissionDateInput() {
        return this.emissionDateInput.getAttribute('value');
    }

    async setDocumentCodeInput(documentCode) {
        await this.documentCodeInput.sendKeys(documentCode);
    }

    async getDocumentCodeInput() {
        return this.documentCodeInput.getAttribute('value');
    }

    async setGlosaInput(glosa) {
        await this.glosaInput.sendKeys(glosa);
    }

    async getGlosaInput() {
        return this.glosaInput.getAttribute('value');
    }

    async setImageInput(image) {
        await this.imageInput.sendKeys(image);
    }

    async getImageInput() {
        return this.imageInput.getAttribute('value');
    }

    async documentTypePurchaseSelectLastOption() {
        await this.documentTypePurchaseSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async documentTypePurchaseSelectOption(option) {
        await this.documentTypePurchaseSelect.sendKeys(option);
    }

    getDocumentTypePurchaseSelect(): ElementFinder {
        return this.documentTypePurchaseSelect;
    }

    async getDocumentTypePurchaseSelectedOption() {
        return this.documentTypePurchaseSelect.element(by.css('option:checked')).getText();
    }

    async paymentTypeSelectLastOption() {
        await this.paymentTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async paymentTypeSelectOption(option) {
        await this.paymentTypeSelect.sendKeys(option);
    }

    getPaymentTypeSelect(): ElementFinder {
        return this.paymentTypeSelect;
    }

    async getPaymentTypeSelectedOption() {
        return this.paymentTypeSelect.element(by.css('option:checked')).getText();
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

export class ProviderPaymentDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-providerPayment-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-providerPayment'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
