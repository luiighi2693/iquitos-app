import { element, by, ElementFinder } from 'protractor';

export class SellComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-sell div table .btn-danger'));
    title = element.all(by.css('jhi-sell div h2#page-heading span')).first();

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

export class SellUpdatePage {
    pageTitle = element(by.id('jhi-sell-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    codeInput = element(by.id('field_code'));
    subTotalAmountInput = element(by.id('field_subTotalAmount'));
    taxAmountInput = element(by.id('field_taxAmount'));
    totalAmountInput = element(by.id('field_totalAmount'));
    dateInput = element(by.id('field_date'));
    statusSelect = element(by.id('field_status'));
    glossInput = element(by.id('field_gloss'));
    clientSelect = element(by.id('field_client'));
    employeeSelect = element(by.id('field_employee'));
    boxSelect = element(by.id('field_box'));
    documentTypeSellSelect = element(by.id('field_documentTypeSell'));
    paymentTypeSelect = element(by.id('field_paymentType'));
    productsDeliveredStatusSelect = element(by.id('field_productsDeliveredStatus'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setCodeInput(code) {
        await this.codeInput.sendKeys(code);
    }

    async getCodeInput() {
        return this.codeInput.getAttribute('value');
    }

    async setSubTotalAmountInput(subTotalAmount) {
        await this.subTotalAmountInput.sendKeys(subTotalAmount);
    }

    async getSubTotalAmountInput() {
        return this.subTotalAmountInput.getAttribute('value');
    }

    async setTaxAmountInput(taxAmount) {
        await this.taxAmountInput.sendKeys(taxAmount);
    }

    async getTaxAmountInput() {
        return this.taxAmountInput.getAttribute('value');
    }

    async setTotalAmountInput(totalAmount) {
        await this.totalAmountInput.sendKeys(totalAmount);
    }

    async getTotalAmountInput() {
        return this.totalAmountInput.getAttribute('value');
    }

    async setDateInput(date) {
        await this.dateInput.sendKeys(date);
    }

    async getDateInput() {
        return this.dateInput.getAttribute('value');
    }

    async setStatusSelect(status) {
        await this.statusSelect.sendKeys(status);
    }

    async getStatusSelect() {
        return this.statusSelect.element(by.css('option:checked')).getText();
    }

    async statusSelectLastOption() {
        await this.statusSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setGlossInput(gloss) {
        await this.glossInput.sendKeys(gloss);
    }

    async getGlossInput() {
        return this.glossInput.getAttribute('value');
    }

    async clientSelectLastOption() {
        await this.clientSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async clientSelectOption(option) {
        await this.clientSelect.sendKeys(option);
    }

    getClientSelect(): ElementFinder {
        return this.clientSelect;
    }

    async getClientSelectedOption() {
        return this.clientSelect.element(by.css('option:checked')).getText();
    }

    async employeeSelectLastOption() {
        await this.employeeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async employeeSelectOption(option) {
        await this.employeeSelect.sendKeys(option);
    }

    getEmployeeSelect(): ElementFinder {
        return this.employeeSelect;
    }

    async getEmployeeSelectedOption() {
        return this.employeeSelect.element(by.css('option:checked')).getText();
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

    async documentTypeSellSelectLastOption() {
        await this.documentTypeSellSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async documentTypeSellSelectOption(option) {
        await this.documentTypeSellSelect.sendKeys(option);
    }

    getDocumentTypeSellSelect(): ElementFinder {
        return this.documentTypeSellSelect;
    }

    async getDocumentTypeSellSelectedOption() {
        return this.documentTypeSellSelect.element(by.css('option:checked')).getText();
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

    async productsDeliveredStatusSelectLastOption() {
        await this.productsDeliveredStatusSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async productsDeliveredStatusSelectOption(option) {
        await this.productsDeliveredStatusSelect.sendKeys(option);
    }

    getProductsDeliveredStatusSelect(): ElementFinder {
        return this.productsDeliveredStatusSelect;
    }

    async getProductsDeliveredStatusSelectedOption() {
        return this.productsDeliveredStatusSelect.element(by.css('option:checked')).getText();
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

export class SellDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-sell-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-sell'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
