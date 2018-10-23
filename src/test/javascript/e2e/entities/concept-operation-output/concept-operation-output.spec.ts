/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    ConceptOperationOutputComponentsPage,
    ConceptOperationOutputDeleteDialog,
    ConceptOperationOutputUpdatePage
} from './concept-operation-output.page-object';

const expect = chai.expect;

describe('ConceptOperationOutput e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let conceptOperationOutputUpdatePage: ConceptOperationOutputUpdatePage;
    let conceptOperationOutputComponentsPage: ConceptOperationOutputComponentsPage;
    let conceptOperationOutputDeleteDialog: ConceptOperationOutputDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ConceptOperationOutputs', async () => {
        await navBarPage.goToEntity('concept-operation-output');
        conceptOperationOutputComponentsPage = new ConceptOperationOutputComponentsPage();
        expect(await conceptOperationOutputComponentsPage.getTitle()).to.eq('Concept Operation Outputs');
    });

    it('should load create ConceptOperationOutput page', async () => {
        await conceptOperationOutputComponentsPage.clickOnCreateButton();
        conceptOperationOutputUpdatePage = new ConceptOperationOutputUpdatePage();
        expect(await conceptOperationOutputUpdatePage.getPageTitle()).to.eq('Create or edit a Concept Operation Output');
        await conceptOperationOutputUpdatePage.cancel();
    });

    it('should create and save ConceptOperationOutputs', async () => {
        const nbButtonsBeforeCreate = await conceptOperationOutputComponentsPage.countDeleteButtons();

        await conceptOperationOutputComponentsPage.clickOnCreateButton();
        await promise.all([
            conceptOperationOutputUpdatePage.setValueInput('value'),
            conceptOperationOutputUpdatePage.setMetaDataInput('metaData')
        ]);
        expect(await conceptOperationOutputUpdatePage.getValueInput()).to.eq('value');
        expect(await conceptOperationOutputUpdatePage.getMetaDataInput()).to.eq('metaData');
        await conceptOperationOutputUpdatePage.save();
        expect(await conceptOperationOutputUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await conceptOperationOutputComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ConceptOperationOutput', async () => {
        const nbButtonsBeforeDelete = await conceptOperationOutputComponentsPage.countDeleteButtons();
        await conceptOperationOutputComponentsPage.clickOnLastDeleteButton();

        conceptOperationOutputDeleteDialog = new ConceptOperationOutputDeleteDialog();
        expect(await conceptOperationOutputDeleteDialog.getDialogTitle()).to.eq(
            'Are you sure you want to delete this Concept Operation Output?'
        );
        await conceptOperationOutputDeleteDialog.clickOnConfirmButton();

        expect(await conceptOperationOutputComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
