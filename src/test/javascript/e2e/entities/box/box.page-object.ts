import { element, by, ElementFinder } from 'protractor';

export class BoxComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-box div table .btn-danger'));
    title = element.all(by.css('jhi-box div h2#page-heading span')).first();

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

export class BoxUpdatePage {
    pageTitle = element(by.id('jhi-box-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    initAmountInput = element(by.id('field_initAmount'));
    actualAmountInput = element(by.id('field_actualAmount'));
    initDateInput = element(by.id('field_initDate'));
    endDateInput = element(by.id('field_endDate'));
    boxSelect = element(by.id('field_box'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setInitAmountInput(initAmount) {
        await this.initAmountInput.sendKeys(initAmount);
    }

    async getInitAmountInput() {
        return this.initAmountInput.getAttribute('value');
    }

    async setActualAmountInput(actualAmount) {
        await this.actualAmountInput.sendKeys(actualAmount);
    }

    async getActualAmountInput() {
        return this.actualAmountInput.getAttribute('value');
    }

    async setInitDateInput(initDate) {
        await this.initDateInput.sendKeys(initDate);
    }

    async getInitDateInput() {
        return this.initDateInput.getAttribute('value');
    }

    async setEndDateInput(endDate) {
        await this.endDateInput.sendKeys(endDate);
    }

    async getEndDateInput() {
        return this.endDateInput.getAttribute('value');
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

export class BoxDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-box-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-box'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
