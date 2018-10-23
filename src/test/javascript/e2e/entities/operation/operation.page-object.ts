import { element, by, ElementFinder } from 'protractor';

export class OperationComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-operation div table .btn-danger'));
    title = element.all(by.css('jhi-operation div h2#page-heading span')).first();

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

export class OperationUpdatePage {
    pageTitle = element(by.id('jhi-operation-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    initDateInput = element(by.id('field_initDate'));
    glossInput = element(by.id('field_gloss'));
    amountInput = element(by.id('field_amount'));
    operationTypeSelect = element(by.id('field_operationType'));
    boxSelect = element(by.id('field_box'));
    paymentTypeSelect = element(by.id('field_paymentType'));
    conceptOperationInputSelect = element(by.id('field_conceptOperationInput'));
    conceptOperationOutputSelect = element(by.id('field_conceptOperationOutput'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setInitDateInput(initDate) {
        await this.initDateInput.sendKeys(initDate);
    }

    async getInitDateInput() {
        return this.initDateInput.getAttribute('value');
    }

    async setGlossInput(gloss) {
        await this.glossInput.sendKeys(gloss);
    }

    async getGlossInput() {
        return this.glossInput.getAttribute('value');
    }

    async setAmountInput(amount) {
        await this.amountInput.sendKeys(amount);
    }

    async getAmountInput() {
        return this.amountInput.getAttribute('value');
    }

    async setOperationTypeSelect(operationType) {
        await this.operationTypeSelect.sendKeys(operationType);
    }

    async getOperationTypeSelect() {
        return this.operationTypeSelect.element(by.css('option:checked')).getText();
    }

    async operationTypeSelectLastOption() {
        await this.operationTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
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

    async conceptOperationInputSelectLastOption() {
        await this.conceptOperationInputSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async conceptOperationInputSelectOption(option) {
        await this.conceptOperationInputSelect.sendKeys(option);
    }

    getConceptOperationInputSelect(): ElementFinder {
        return this.conceptOperationInputSelect;
    }

    async getConceptOperationInputSelectedOption() {
        return this.conceptOperationInputSelect.element(by.css('option:checked')).getText();
    }

    async conceptOperationOutputSelectLastOption() {
        await this.conceptOperationOutputSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async conceptOperationOutputSelectOption(option) {
        await this.conceptOperationOutputSelect.sendKeys(option);
    }

    getConceptOperationOutputSelect(): ElementFinder {
        return this.conceptOperationOutputSelect;
    }

    async getConceptOperationOutputSelectedOption() {
        return this.conceptOperationOutputSelect.element(by.css('option:checked')).getText();
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

export class OperationDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-operation-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-operation'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
