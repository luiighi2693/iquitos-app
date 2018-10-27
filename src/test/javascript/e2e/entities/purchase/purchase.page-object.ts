import { element, by, ElementFinder } from 'protractor';

export class PurchaseComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-purchase div table .btn-danger'));
    title = element.all(by.css('jhi-purchase div h2#page-heading span')).first();

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

export class PurchaseUpdatePage {
    pageTitle = element(by.id('jhi-purchase-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    dateInput = element(by.id('field_date'));
    remisionGuideInput = element(by.id('field_remisionGuide'));
    documentNumberInput = element(by.id('field_documentNumber'));
    locationSelect = element(by.id('field_location'));
    totalAmountInput = element(by.id('field_totalAmount'));
    correlativeInput = element(by.id('field_correlative'));
    paymentPurchaseTypeSelect = element(by.id('field_paymentPurchaseType'));
    metaDataInput = element(by.id('field_metaData'));
    providerSelect = element(by.id('field_provider'));
    documentTypePurchaseSelect = element(by.id('field_documentTypePurchase'));
    purchaseStatusSelect = element(by.id('field_purchaseStatus'));
    boxSelect = element(by.id('field_box'));
    productsSelect = element(by.id('field_products'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setDateInput(date) {
        await this.dateInput.sendKeys(date);
    }

    async getDateInput() {
        return this.dateInput.getAttribute('value');
    }

    async setRemisionGuideInput(remisionGuide) {
        await this.remisionGuideInput.sendKeys(remisionGuide);
    }

    async getRemisionGuideInput() {
        return this.remisionGuideInput.getAttribute('value');
    }

    async setDocumentNumberInput(documentNumber) {
        await this.documentNumberInput.sendKeys(documentNumber);
    }

    async getDocumentNumberInput() {
        return this.documentNumberInput.getAttribute('value');
    }

    async setLocationSelect(location) {
        await this.locationSelect.sendKeys(location);
    }

    async getLocationSelect() {
        return this.locationSelect.element(by.css('option:checked')).getText();
    }

    async locationSelectLastOption() {
        await this.locationSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setTotalAmountInput(totalAmount) {
        await this.totalAmountInput.sendKeys(totalAmount);
    }

    async getTotalAmountInput() {
        return this.totalAmountInput.getAttribute('value');
    }

    async setCorrelativeInput(correlative) {
        await this.correlativeInput.sendKeys(correlative);
    }

    async getCorrelativeInput() {
        return this.correlativeInput.getAttribute('value');
    }

    async setPaymentPurchaseTypeSelect(paymentPurchaseType) {
        await this.paymentPurchaseTypeSelect.sendKeys(paymentPurchaseType);
    }

    async getPaymentPurchaseTypeSelect() {
        return this.paymentPurchaseTypeSelect.element(by.css('option:checked')).getText();
    }

    async paymentPurchaseTypeSelectLastOption() {
        await this.paymentPurchaseTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setMetaDataInput(metaData) {
        await this.metaDataInput.sendKeys(metaData);
    }

    async getMetaDataInput() {
        return this.metaDataInput.getAttribute('value');
    }

    async providerSelectLastOption() {
        await this.providerSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async providerSelectOption(option) {
        await this.providerSelect.sendKeys(option);
    }

    getProviderSelect(): ElementFinder {
        return this.providerSelect;
    }

    async getProviderSelectedOption() {
        return this.providerSelect.element(by.css('option:checked')).getText();
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

    async purchaseStatusSelectLastOption() {
        await this.purchaseStatusSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async purchaseStatusSelectOption(option) {
        await this.purchaseStatusSelect.sendKeys(option);
    }

    getPurchaseStatusSelect(): ElementFinder {
        return this.purchaseStatusSelect;
    }

    async getPurchaseStatusSelectedOption() {
        return this.purchaseStatusSelect.element(by.css('option:checked')).getText();
    }

    async boxSelectLastOption() {
        await this.boxSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async boxSelectOption(option) {
        await this.boxSelect.sendKeys(option);
    }

    getBoxSelect(): ElementFinder {
        return this.boxSelect;
    }

    async getBoxSelectedOption() {
        return this.boxSelect.element(by.css('option:checked')).getText();
    }

    async productsSelectLastOption() {
        await this.productsSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async productsSelectOption(option) {
        await this.productsSelect.sendKeys(option);
    }

    getProductsSelect(): ElementFinder {
        return this.productsSelect;
    }

    async getProductsSelectedOption() {
        return this.productsSelect.element(by.css('option:checked')).getText();
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

export class PurchaseDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-purchase-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-purchase'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
