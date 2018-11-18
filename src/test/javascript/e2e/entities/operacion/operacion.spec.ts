/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OperacionComponentsPage, OperacionDeleteDialog, OperacionUpdatePage } from './operacion.page-object';

const expect = chai.expect;

describe('Operacion e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let operacionUpdatePage: OperacionUpdatePage;
    let operacionComponentsPage: OperacionComponentsPage;
    let operacionDeleteDialog: OperacionDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Operacions', async () => {
        await navBarPage.goToEntity('operacion');
        operacionComponentsPage = new OperacionComponentsPage();
        expect(await operacionComponentsPage.getTitle()).to.eq('Operacions');
    });

    it('should load create Operacion page', async () => {
        await operacionComponentsPage.clickOnCreateButton();
        operacionUpdatePage = new OperacionUpdatePage();
        expect(await operacionUpdatePage.getPageTitle()).to.eq('Create or edit a Operacion');
        await operacionUpdatePage.cancel();
    });

    it('should create and save Operacions', async () => {
        const nbButtonsBeforeCreate = await operacionComponentsPage.countDeleteButtons();

        await operacionComponentsPage.clickOnCreateButton();
        await promise.all([
            operacionUpdatePage.setFechaInput('2000-12-31'),
            operacionUpdatePage.setGlosaInput('glosa'),
            operacionUpdatePage.setMontoInput('5'),
            operacionUpdatePage.tipoDeOperacionSelectLastOption(),
            operacionUpdatePage.cajaSelectLastOption(),
            operacionUpdatePage.tipoDePagoSelectLastOption(),
            operacionUpdatePage.tipoDeOperacionDeIngresoSelectLastOption(),
            operacionUpdatePage.tipoDeOperacionDeGastoSelectLastOption()
        ]);
        expect(await operacionUpdatePage.getFechaInput()).to.eq('2000-12-31');
        expect(await operacionUpdatePage.getGlosaInput()).to.eq('glosa');
        expect(await operacionUpdatePage.getMontoInput()).to.eq('5');
        await operacionUpdatePage.save();
        expect(await operacionUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await operacionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Operacion', async () => {
        const nbButtonsBeforeDelete = await operacionComponentsPage.countDeleteButtons();
        await operacionComponentsPage.clickOnLastDeleteButton();

        operacionDeleteDialog = new OperacionDeleteDialog();
        expect(await operacionDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Operacion?');
        await operacionDeleteDialog.clickOnConfirmButton();

        expect(await operacionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
